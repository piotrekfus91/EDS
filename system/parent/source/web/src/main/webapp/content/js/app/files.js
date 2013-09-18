var currentNode;

$(document).ready(function() {
    var files_accordion_props = {
        disabled: true,
        heightStyle: 'fill'
    };
    $('#files_tree_td').accordion(files_accordion_props);
    $('#info_td').accordion(files_accordion_props);
    var height = $('#files_tree_td').height() - 100;
    $('.lazy_fill_height').height(height);
    open_lazy();
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
    });
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
            if(node.data.isFolder) {
                var children = get_children(node.data.key);
                if(children.length > 0) {
                    $.each(children, function() {
                        node.addChild(this);
                    });
                    node.sortChildren(compare_nodes_by_title, false);
                } else {
                    // myk na zablokowanie "koleczka wczytywania ajax"
                    node.expand(false);
                    node.expand(true);
                }
            } else {
                node.expand(false);
            }
        },
        onPostInit: function (isReloading, isError) {
            if (isError) {
                post_message_now('error', 'Błąd wczytywania katalogu głównego');
            } else {
                var node = $("#files_tree").dynatree("getRoot");
                node.toggleExpand();
            }
        },
        onActivate: function (node) {
            currentNode = node;
            if(node.data.isFolder) {
                update_directory_info(node.data.key);
            } else {
                update_document_info(node.data.key);
            }
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
    $('#files_tree .dynatree-container').css('border', '0px solid white');
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
            } else if(key == "share") {
                var docOrDir = currentNode.data.isFolder ? 'directory' : 'document';
                $.ajax({
                    type: "GET",
                    url: rest('/resourceGroups/my/sharable/' + docOrDir + '/' + currentNode.data.key),
                    async: false,
                    success: function(result) {
                        if(is_success(result)) {
                            post_sharable_groups_and_open_dialog(result.data);
                        } else {
                            post_error_from_result(result);
                        }
                    },
                    error: function() {
                        post_message_now('error', 'Błąd przy wczytywaniu grup zasobów');
                    }
                })
            } else if(key == "tag") {
                var current_tag_list = $('#files_tags').text();
                var tag_list_input = $('#tag_list_input');
                tag_list_input.val(current_tag_list);
                autocomplete_set(tag_list_input);
                $('#tag_list_div').dialog("open");
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
            "share": {
                name: "Publikuj",
                icon: "share"
            },
            "tag": {
                "name" : "Edytuj tagi",
                icon: "edit",
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
        async: false,
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
        tagHtml += "<a href=\"javascript:activate_tab(3);javascript:search_tags_by_name('" + this.value + "')\">";
            tagHtml += this.value;
        tagHtml += "</a>"
    });
    tags_div.html(tagHtml);
}

function update_tags_of_document(id, tagList) {
    $.ajax({
        type: "PUT",
        url: rest('/tags/tagsToDocument/' + id + '/' + tagList),
        success: function(result) {
            if(is_success(result)) {
                post_message_now('success', 'Lista tagów zaktualizowana');
                currentNode.reloadChildren();
                update_document_info(id);
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy aktualizacji listy tagów');
        }
    });
}

function post_sharable_groups_and_open_dialog(resource_groups) {
    var shared_resource_groups_div = $('#shared_resource_groups_div');
    shared_resource_groups_div.find('#shared_resource_groups_file_name').text(currentNode.data.title);
    shared_resource_groups_div.find('#shared_resource_groups_doc_or_dir').text(currentNode.data.isFolder ? "katalog" : "dokument");
    var content = "";
    $.each(resource_groups, function() {
        content += "<input type=\"checkbox\" id=\"" + this.name + "\" name=\"" + this.name + "\"";
        if(this.shared) content += " checked=\"checked\"";
        content += " />";
        content += "<label for=\"" + this.name + "\">" + this.name + "</label>";
    });
    shared_resource_groups_div.find('#shared_resource_groups').html(content).buttonset();
    shared_resource_groups_div.dialog("open");
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

$('#tag_list_div').dialog({
    autoOpen: false,
    height: 200,
    width: 400,
    modal: true,
    buttons: {
        "Aktualizuj listę tagów": function() {
            var id = currentNode.data.key;
            var tagList = $('#tag_list_input').val();
            update_tags_of_document(id, tagList);
            clear_and_close_tag_list_div();
        },
        "Anuluj": function() {
            clear_and_close_tag_list_div();
        }
    }
});

$('#shared_resource_groups_div').dialog({
    autoOpen: false,
    height: 'auto',
    width: 'auto',
    modal: true,
    buttons: {
        "Zapisz": function() {
            var docOrDir = currentNode.data.isFolder ? "directory" : "document";
            var data = serialize_form('shared_resource_groups_form');
            $.ajax({
                type: "PUT",
                url: rest('/resourceGroups/share/' + docOrDir + '/' + currentNode.data.key),
                dataType: "json",
                async: false,
                contentType: "application/json; charset=utf-8",
                data: data,
                success: function(result) {
                    if(is_success(result)) {
                        post_message_now('success', 'Zaktualizowano publikowanie pliku/dokumentu');
                    } else {
                        post_error_from_result(result);
                    }
                },
                error: function() {
                    post_message_now('error', 'Błąd podczas publikowania pliku/dokumentu');
                }
            });
            clear_and_close_shared_resource_group_div();
        },
        "Anuluj": function() {
            clear_and_close_shared_resource_group_div();
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
    rename_directory_div.find('#path').text('');
    rename_directory_div.find('#rename_old_name').text('');
    rename_directory_div.find('#rename_new_name').val('');
    rename_directory_div.dialog("close");
}

function clear_and_close_tag_list_div() {
    var tag_list_div = $('#tag_list_div');
    tag_list_div.find('#tag_list_input').val('');
    tag_list_div.dialog("close");
}

function clear_and_close_shared_resource_group_div() {
    var shared_resource_groups_div = $('#shared_resource_groups_div');
    shared_resource_groups_div.find('#shared_resource_groups').html('');
    shared_resource_groups_div.find('#shared_resource_groups_file_name').text('');
    shared_resource_groups_div.dialog("close");
}

function compare_nodes_by_title(node1, node2) {
    var title1 = node1.data.title.toLocaleLowerCase();
    var title2 = node2.data.title.toLocaleLowerCase();
    if(title1 == title2)
        return 0;
    else
        return title1 > title2 ? 1 : -1;
}

function serialize_form(formId) {
    var result = [];
    $('#' + formId + ' :input').each(function() {
        var checkbox = $(this);
        var label = $('label[for="'+checkbox.attr('id')+'"]');
        var entry = {};
        entry['name'] = label.text();
        if(checkbox.is(':checked'))
            entry['shared'] = true;
        else
            entry['shared'] = false;
        result.push(entry);
    });
    return JSON.stringify(result);
}