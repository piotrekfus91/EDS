var currentNode;

$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: rest('/directories/root'),
        success: function(result) {
            if(is_success(result)) {
                var root = result.data;
                init_tree(root);
            } else {
                post_message_now('error', result.error_message);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd wczytywania katalogu głównego');
        }
    })
});

function init_tree(root) {
    $('#files_tree').dynatree({
        fx: {
            height: 'toggle',
            duration: 200
        },
        children: root,
        onCreate: function (node, span) {
            bind_context_menu_for_file_system_entries(span);
        },
        onLazyRead: function (node) {
            var children = get_children(node.data.key);
            if(children.length > 0) {
                $.each(children, function() {
                    node.addChild(this);
                });
                node.sortChildren(compare_nodes_by_title, false);
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
            if(node.data.isFolder)
                update_directory_info(node.data.key);
            else
                update_document_info(node.data.key);
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
                var success;
                console.log("moving " + sourceNode.data.title + " to " + targetNode.data.title);
                if(!sourceNode.data.isFolder)
                    success = move_document(sourceNode.data.key, targetNode.data.key);
                else
                    success = move_directory(sourceNode.data.key, targetNode.data.key);
                if(success) {
                    sourceNode.move(targetNode, hitMode);
                    targetNode.getParent().sortChildren(compare_nodes_by_title, false);
                }
            },
            autoExpandMS: 500,
            preventVoidMoves: true
        }
    });
}

function get_children(key) {
    var children = [];
    $.ajax({
        type: "GET",
        async: false,
        url: rest('/directories/' + key),
        success: function(result) {
            if(is_success(result)) {
                children = result.data;
            } else {
                post_message_now('error', 'Błąd wczytywania katalogu');
            }
        }
    });
    return children;
}

function bind_context_menu_for_file_system_entries() {
    $('#files_tree').contextMenu({
        selector: 'li',
        callback: function(key) {
            if(key == "add") {
                $('#add_directory').dialog("open");
            } else if(key == "download") {
                download_document(currentNode.data.key);
            } else if(key == "delete") {
                if(currentNode.data.isFolder)
                    delete_directory(currentNode.data.key);
                else
                    delete_document(currentNode.data.key);
            } else if(key == "rename") {
                var rename_div = $('#rename_div');
                $.ajax({
                    type: "GET",
                    dataType: "json",
                    url: rest("/directories/single/" + currentNode.data.key),
                    success: function(result) {
                        if(is_success(result))
                            rename_div.find("#path").text(result.data.stringPath);
                    }
                });
                rename_div.find('#rename_old_name').text(currentNode.data.title);
                rename_div.find('#rename_new_name').val(currentNode.data.title);
                rename_div.dialog("open");
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
            "download": {
                name: "Pobierz",
                icon: "download",
                disabled: function() {
                    return currentNode.data.isFolder;
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


function delete_directory(id) {
    $.ajax({
        type: "DELETE",
        url: rest("/directories/delete/" + id),
        success: function(result) {
            if(is_success(result)) {
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

function update_directory_info(directoryId) {
    $.ajax({
        type: "GET",
        url: rest("/directories/single/" + directoryId),
        success: function(result) {
            if(is_success(result)) {
                $('.files_document').css('display', 'none');
                var info = result.data;
                $('#files_type').text('Katalog');
                $('#files_name').text(info.title);
                $('#files_path').text(info.stringPath);
                $('#files_created').text('n/d');
                $('#files_mime').text('n/d');
                $('#files_tags').text('n/d');
                clear_comments();
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu szczegółów katalogu');
        }
    });
}

function update_document_info(documentId) {
    $.ajax({
        type: "GET",
        url: rest("/documents/single/" + documentId),
        success: function(result) {
            if(is_success(result)) {
                $('.files_document').css('display', 'block');
                var info = result.data;
                $('#files_type').text('Dokument');
                $('#files_name').text(info.title);
                $('#files_path').text(info.stringPath);
                $('#files_created').text(info.created);
                $('#files_mime').text(info.mime);
                update_tags(info.tags);
                clear_comments();
                add_comments(info.comments);
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu szczegółów dokumentu');
        }
    });
}

function move_directory(directoryId, destinationDirectoryId) {
    var success = false;
    $.ajax({
        method: "PUT",
        url: rest('/directories/move/' + directoryId + '/' + destinationDirectoryId),
        async: false,
        success: function(result) {
            if(is_success(result)) {
                success = true;
            } else {
                post_error_from_result(result);
            }
        }
    });
    return success;
}

function add_directory(parentDirectoryId, name) {
    $.ajax({
        type: "POST",
        url: rest("/directories/create/" + parentDirectoryId + "/" + name),
        success: function(result) {
            if(is_success(result)) {
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
            if(is_success(result)) {
                post_message_now('success', 'Nazwa katalogu została zmieniona na ' + name);
                currentNode.data.title = name;
                currentNode.render();
            } else {
                post_error_from_result(result);
            }
            clear_and_close_rename_div();
        },
        error: function() {
            post_message_now('error', 'Błąd przy zmianie nazwy');
            clear_and_close_rename_div();
        }
    });
}

function download_document(documentId) {
    window.location = rest('/documents/download/' + documentId);
}

function rename_document(id, name) {
    $.ajax({
        type: "PUT",
        url: rest("/documents/rename/" + id + "/" + name),
        success: function(result) {
            if(is_success(result)) {
                post_message_now('success', 'Nazwa pliku została zmieniona na ' + name);
                currentNode.data.title = name;
                currentNode.render();
            } else {
                post_error_from_result(result);
            }
            clear_and_close_rename_div();
        },
        error: function() {
            post_message_now('error', 'Błąd przy zmianie nazwy');
            clear_and_close_rename_div();
        }
    });
}

function move_document(documentId, destinationDirectoryId) {
    var success = false;
    $.ajax({
        method: "PUT",
        url: rest('/documents/move/' + documentId + '/' + destinationDirectoryId),
        async: false,
        success: function(result) {
            if(is_success(result)) {
                success = true;
            } else {
                post_error_from_result(result);
            }
        }
    });
    return success;
}

function delete_document(documentId) {
    $.ajax({
        method: "DELETE",
        url: rest('/documents/delete/' + documentId),
        success: function(result) {
            if(is_success(result)) {
                post_message_now('success', 'Plik został usunięty');
                currentNode.remove();
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy usuwaniu pliku');
        }
    });
}

function add_comments(comments) {
    var comments_div = $('#files_comments');
    $.each(comments, function(index) {
        var comment_div_class = index % 2 == 0 ? 'files_comment_even' : 'files_comment_odd';
        var commentBody = "<div class=\"" + comment_div_class + "\">";
            commentBody += "<div class=\"files_comment_content\">";
                commentBody += this.content;
            commentBody += "</div>";
            commentBody += "<div class=\"files_comment_footer\">";
                commentBody += "<span class=\"files_comment_author\">";
                    commentBody += this.user;
                commentBody += "</span>,&nbsp;";
                commentBody += "<span class=\"files_comment_date\">";
                    commentBody += this.created;
                commentBody += "</span>";
            commentBody += "</div>";
        commentBody += "</div>";
        comments_div.append(commentBody);
    });
}

function clear_comments() {
    $('#files_comments').html('');
}

function update_tags(tags) {
    var tags_div = $('#files_tags');
    tags_div.html('');
    var tagHtml = "";
    $.each(tags, function(index) {
        if(index > 0)
            tagHtml += ", ";
        tagHtml += "<a href=\"javascript:activate_tab('eds/v/app.Search')\">";
            tagHtml += this.value;
        tagHtml += "</a>"
    });
    tags_div.html(tagHtml);
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

$('#rename_div').dialog({
    autoOpen: false,
    height: 300,
    width: 400,
    modal: true,
    buttons: {
        "Zmień nazwę": function() {
            var id = currentNode.data.key;
            var newName = $('#rename_new_name').val();
            if(currentNode.data.isFolder)
                rename_directory(id, newName);
            else
                rename_document(id, newName);
        },
        "Anuluj": function() {
            clear_and_close_rename_div();
        }
    }
});

function clear_and_close_add_directory_div() {
    var add_directory_div = $('#add_directory');
    add_directory_div.find('#add_directory_value').val('');
    add_directory_div.dialog("close");
}

function clear_and_close_rename_div() {
    var rename_directory_div = $('#rename_div');
    rename_directory_div.find("#path").text("");
    rename_directory_div.find('#rename_old_name').text("");
    rename_directory_div.find('#rename_new_name').val('');
    rename_directory_div.dialog("close");
}

function compare_nodes_by_title(node1, node2) {
    var title1 = node1.data.title.toLocaleLowerCase();
    var title2 = node2.data.title.toLocaleLowerCase();
    if(title1 == title2)
        return 0;
    else
        return title1 > title2 ? 1 : -1;
}

function is_success(result) {
    return result.result == "SUCCESS";
}