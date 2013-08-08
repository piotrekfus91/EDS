var currentNode;

$(document).ready(function() {
    $('#files_tree').dynatree({
        fx: {
            height: 'toggle',
            duration: 200
        },
        initAjax: {
            url: rest('/directories/root')
        },
        onCreate: function(node, span) {
            bindContextMenuForFileSystemEntries(span);
        },
        onLazyRead: function(node) {
            node.appendAjax({
                url: rest('/directories/' + node.data.key)
            });
        },
        onPostInit: function(isReloading, isError) {
            if(isError) {
                post_message_now('error', 'Błąd wczytywania katalogu głównego');
            } else {
                post_message_now('success', 'Wczytano katalog główny');
            }
        },
        onActivate: function(node) {
            currentNode = node;
        }
    });
});

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
                rename_directory_div.find('#new_directory_name').attr('value', currentNode.data.title);
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
        success: function(refreshedDir) {
            if(refreshedDir) {
                post_message_now('information', 'Katalog usunięty: ' + currentNode.data.title);
                currentNode.remove();
            } else {
                post_message_now('error', 'Błąd przy usuwaniu katalogu: ' + currentNode.data.title);
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
        success: function() {
            post_message_now('success', 'Dodano katalog: ' + name);
            currentNode.addChild({
                title: name,
                isFolder: true
            });
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
        success: function() {
            post_message_now('success', 'Nazwa katalogu została zmieniona na ' + name);
            currentNode.data.title = name;
            currentNode.render();
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
    add_directory_div.find('#add_directory_value').attr('value', '');
    add_directory_div.dialog("close");
}

function clear_and_close_rename_directory_div() {
    var rename_directory_div = $('#rename_directory');
    rename_directory_div.find("#path").text("");
    rename_directory_div.find('#old_directory_name').text("");
    rename_directory_div.find('#new_directory_name').attr('value', "");
    rename_directory_div.dialog("close");
}