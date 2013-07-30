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
            bindContextMenu(span);
        },
        onLazyRead: function(node) {
            node.appendAjax({
                url: rest('/directories/' + node.data.key)
            });
        },
        onPostInit: function(isReloading, isError) {
            if(isError) {
                post_message_now('error', 'Błąd wczytywania katalogów głównych');
            } else {
                post_message_now('success', 'Wczytano katalogi główne');
            }
        },
        onActivate: function(node) {
            currentNode = node;
        }
    });
});

function bindContextMenu() {
    $('#files_tree').contextMenu({
        selector: 'li',
        callback: function(key, options) {
            if(key == "delete") {
                delete_file_system_entry(currentNode.data.key);
            } else if(key == "rename") {
                var rename_directory_div = $('#rename_directory');
                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: rest("/directories/single/" + currentNode.data.key),
                    success: function(dir) {
                        console.log(dir);
                        console.log(dir.stringPath);
                        rename_directory_div.find("#path").text(dir.stringPath);
                    }
                });
                rename_directory_div.find('#old_directory_name').text(currentNode.data.title);
                rename_directory_div.find('#new_directory_name').attr('value', currentNode.data.title);
                rename_directory_div.dialog("open");
            }
        },
        items: {
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

function rename_directory(id, name) {
    $.ajax({
        type: "PUT",
        url: rest("/directories/rename/" + id + "/" + name),
        success: function() {
            post_message_now('success', 'Nazwa katalogu została zmieniona na ' + name);
            currentNode.data.title = name;
            currentNode.render();
            clear_and_close_rename_div()
        },
        error: function() {
            post_message_now('error', 'Błąd przy zmianie nazwy');
            clear_and_close_rename_div();
        }
    });
}

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
            clear_and_close_rename_div();
        }
    }
});

function clear_and_close_rename_div() {
    var rename_directory_div = $('#rename_directory');
    rename_directory_div.find("#path").text("");
    rename_directory_div.find('#old_directory_name').text("");
    rename_directory_div.find('#new_directory_name').attr('value', "");
    rename_directory_div.dialog("close");
}