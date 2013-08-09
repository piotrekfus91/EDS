var currentNode;

$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: rest('/directories/root'),
        success: function(result) {
            if(isSuccess(result)) {
                var root = result.data;
                initTree(root);
            } else {
                post_message_now('error', result.error_message);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd wczytywania katalogu głównego');
        }
    })
});

function initTree(root) {
    $('#files_tree').dynatree({
        fx: {
            height: 'toggle',
            duration: 200
        },
        children: root,
        onCreate: function (node, span) {
            bindContextMenuForFileSystemEntries(span);
        },
        onLazyRead: function (node) {
            var children = getChildren(node.data.key);
            if(children.length > 0) {
                $.each(children, function() {
                    node.addChild(this);
                });
            } else {
                node.expand(false);
                node.expand(true);
            }
        },
        onPostInit: function (isReloading, isError) {
            if (isError) {
                post_message_now('error', 'Błąd wczytywania katalogu głównego');
            } else {
                post_message_now('success', 'Wczytano katalog główny');
            }
        },
        onActivate: function (node) {
            currentNode = node;
        },
        dnd: {
            onDragStart: function () {
                return true;
            },
            onDragOver: function (targetNode, sourceNode) {
                if (!targetNode.data.isFolder || targetNode.isDescendantOf(sourceNode))
                    return false;
                return true;
            },
            onDragEnter: function (targetNode) {
                return targetNode.data.isFolder;
            },
            onDrop: function (targetNode, sourceNode, hitMode) {
                sourceNode.move(targetNode, hitMode);
                targetNode.getParent().sortChildren(compareNodesByTitle, false);
            },
            autoExpandMS: 500,
            preventVoidMoves: true
        }
    });
}

function getChildren(key) {
    var children = [];
    $.ajax({
        type: "GET",
        async: false,
        url: rest('/directories/' + key),
        success: function(result) {
            if(isSuccess(result)) {
                children = result.data;
            } else {
                post_message_now('error', 'Błąd wczytywania katalogu');
            }
        }
    });
    return children;
}

function bindContextMenuForFileSystemEntries() {
    $('#files_tree').contextMenu({
        selector: 'li',
        callback: function(key) {
            if(key == "add") {
                $('#add_directory').dialog("open");
            } else if(key == "delete") {
                delete_file_system_entry(currentNode.data.key);
            } else if(key == "rename") {
                var rename_directory_div = $('#rename_directory');
                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: rest("/directories/single/" + currentNode.data.key),
                    success: function(dir) {
                        rename_directory_div.find("#path").text(dir.stringPath);
                    }
                });
                rename_directory_div.find('#old_directory_name').text(currentNode.data.title);
                rename_directory_div.find('#new_directory_name').val(currentNode.data.title);
                rename_directory_div.dialog("open");
            }
        },
        items: {
            "add": {
                name: "Dodaj katalog",
                icon: "add",
                disabled: function() {
                    return !currentNode.data.isFolder;
                }
            },
            "rename": {
                name: "Zmień nazwę",
                icon: "edit"
            },
            "delete": {
                name: "Usuń",
                icon: "delete"
            }
        }
    })
}


function delete_file_system_entry(id) {
    $.ajax({
        type: "DELETE",
        url: rest("/directories/delete/" + id),
        success: function(result) {
            if(isSuccess(result)) {
                post_message_now('information', 'Katalog usunięty: ' + currentNode.data.title);
                currentNode.remove();
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy usuwaniu katalogu');
        }
    });
}

function add_directory(parentDirectoryId, name) {
    $.ajax({
        type: "POST",
        url: rest("/directories/create/" + parentDirectoryId + "/" + name),
        success: function(result) {
            if(isSuccess(result)) {
                post_message_now('success', 'Dodano katalog: ' + name);
                currentNode.addChild({
                    title: name,
                    isFolder: true
                });
            } else {
                post_error_from_result(result);
            }
            clear_and_close_add_directory_div();
        },
        error: function() {
            post_message_now('error', 'Błąd przy dodawaniu katalogu');
            clear_and_close_add_directory_div();
        }
    });
}

function rename_directory(id, name) {
    $.ajax({
        type: "PUT",
        url: rest("/directories/rename/" + id + "/" + name),
        success: function(result) {
            if(isSuccess(result)) {
                post_message_now('success', 'Nazwa katalogu została zmieniona na ' + name);
                currentNode.data.title = name;
                currentNode.render();
            } else {
                post_error_from_result(result);
            }
            clear_and_close_rename_directory_div();
        },
        error: function() {
            post_message_now('error', 'Błąd przy zmianie nazwy');
            clear_and_close_rename_directory_div();
        }
    });
}

$('#add_directory').dialog({
    autoOpen: false,
    height: 150,
    width: 400,
    modal: true,
    buttons: {
        "Dodaj katalog": function() {
            var parentDirectoryId = currentNode.data.key;
            var name = $('#add_directory_value').val();
            add_directory(parentDirectoryId, name);
        },
        "Anuluj": function() {
            clear_and_close_add_directory_div();
        }
    }
});

$('#rename_directory').dialog({
    autoOpen: false,
    height: 300,
    width: 400,
    modal: true,
    buttons: {
        "Zmień nazwę": function() {
            var id = currentNode.data.key;
            var new_name = $('#new_directory_name').val();
            rename_directory(id, new_name);
        },
        "Anuluj": function() {
            clear_and_close_rename_directory_div();
        }
    }
});

function clear_and_close_add_directory_div() {
    var add_directory_div = $('#add_directory');
    add_directory_div.find('#add_directory_value').val('');
    add_directory_div.dialog("close");
}

function clear_and_close_rename_directory_div() {
    var rename_directory_div = $('#rename_directory');
    rename_directory_div.find("#path").text("");
    rename_directory_div.find('#old_directory_name').text("");
    rename_directory_div.find('#new_directory_name').val('');
    rename_directory_div.dialog("close");
}

function compareNodesByTitle(node1, node2) {
    var title1 = node1.data.title.toLocaleLowerCase();
    var title2 = node2.data.title.toLocaleLowerCase();
    if(title1 == title2)
        return 0;
    else
        return title1 > title2 ? 1 : -1;
}

function isSuccess(result) {
    return result.result == "SUCCESS";
}