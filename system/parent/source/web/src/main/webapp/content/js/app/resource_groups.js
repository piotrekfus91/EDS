var saveMode = ""; // add lub edit
var editModeResourceGroupName = "";
var userFriendlyName = "";
var userName;
var groupName = "";
var activeDiv;
var privilegesStatus = {};

$(document).ready(function() {
    $('#resource_groups').accordion();
    $('#create_new_resource_group_button').button();
    $('#create_new_resource_group_button').click(create_new_resource_group_button_click);

    reload_resource_groups();
    open_lazy();
});

function reload_resource_groups() {
    $.ajax({
        type: "GET",
        url: rest("/resourceGroups/my"),
        success: function(result) {
            if(is_success(result)) {
                post_resource_groups(result.data);
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy ładowaniu grup zasobów.');
        }
    });
}

function post_resource_groups(resource_groups) {
    var resource_groups_div = $('#resource_groups');
    resource_groups_div.css('display', 'none');
    resource_groups_div.accordion("destroy");
    resource_groups_div.html('');
    $.each(resource_groups, function() {
        append_resource_group(resource_groups_div, this);
    });
    resource_groups_div.accordion({
        collapsible: true,
        heightStyle: "content",
        active: false
    });
    $("h3", "#resource_groups").click(function() {
        activeDiv = $(this).next('div');
        var active_resource_group = $(this).find("a").attr("title");
        load_resource_group_info(activeDiv, active_resource_group);
    });
    resource_groups_div.css('display', 'block');
}

function append_resource_group(div, resourceGroup) {
    var content = "";
    content += "<h3>";
        content += "<a href=\"#\" title=\"" + resourceGroup.name + "\">";
            content += resourceGroup.name;
        content += "</a>";
    content += "</h3>";
    content += "<div>";
        content += "";
    content += "</div>";
    div.append(content);
}

function load_resource_group_info(div, resourceGroupName) {
    $.ajax({
        type: "GET",
        url: rest("/resourceGroups/name/" + resourceGroupName),
        async: false,
        success: function(result) {
            if(is_success(result)) {
                post_resource_group_info(div, result.data);
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu szczegółów grupy zasobów: ' + resourceGroupName);
        }
    });
}

function post_resource_group_info(div, data) {
    var resource_group = data.resourceGroup;
    var users = data.users;
    groupName = resource_group.name;
    privilegesStatus = data.privilegesStatus;
    div.removeAttr('height');
    var content = "";
    div.html('');
    content += "<div>";
        content += "Założyciel: ";
        content += resource_group.founder;
    content += "</div>";
    content += "<div id=\"resource_group_description\">";
        content += resource_group.description;
    content += "</div>";
    content += "<div id=\"resource_group_buttons\">";
        content += "<button onclick=\"\"";
                if(!has_privilege(privilegesStatus, "manage_roles")) content += " disabled=\"disabled\"";
                content += ">";
            content += "Dodaj nowego użytkownika";
        content += "</button>";
        content += "<button onclick=\"javascript:edit_resource_group_button_click('" + resource_group.name + "')\"";
                if(!has_privilege(privilegesStatus, 'update_info')) content += " disabled=\"disabled\""
                content += ">";
            content += "Edytuj informacje o grupie";
        content += "</button>";
        content += "<button onclick=\"javascript:delete_resource_group('" + resource_group.name + "')\"";
                if(!has_privilege(privilegesStatus, 'delete')) content += " disabled=\"disabled\"";
                content += ">";
            content += "Usuń grupę zasobów";
        content += "</button>";
    content += "</div>";
    content += "<br />";
    content += "<table class=\"resource_group_table resource_group_users_table\">";
    $.each(users, function() {
        content += "<tr>";
            content += "<td>";
                content += this.friendlyName;
            content += "</td>";
            content += "<td>";
                content += "<button onclick=\"javascript:show_user_roles_dialog('" + this.name + "', '" + this.friendlyName + "')\"";
                        if(!has_privilege(privilegesStatus, 'manage_roles')) content += " disabled=\"disabled\"";
                        content += ">";
                    content += "Edytuj role";
                content += "</button>";
            content += "</td>";
        content += "</tr>";
    });
    content += "</table>";
    content += "<table class=\"resource_group_table resource_group_documents_table\">";
    content += "<tr>";
            content += "<th>";
                content += "Nazwa";
            content += "</th>";
            content += "<th>";
                content += "Właściciel";
            content += "</th>";
            content += "<th>";
                content += "MIME";
            content += "</th>";
            content += "<th>";
                content += "Utworzony";
            content += "</th>";
            content += "<th>";
                content += "";
            content += "</th>";
        content += "</tr>"
    if(has_privilege(privilegesStatus, 'list_files')) {
        $.each(resource_group.documents, function() {
            content += "<tr>";
                content += "<td>";
                    content += this.title;
                content += "</td>";
                content += "<td>";
                    content += this.owner;
                content += "</td>";
                content += "<td>";
                    content += this.mime;
                content += "</td>";
                content += "<td>";
                    content += this.created;
                content += "</td>";
                content += "<td>";
                    content += "<div class=\"resource_group_buttons\">";
                        if(has_privilege(privilegesStatus, 'download_files')) {
                            content += "<a href=\"";
                                    content += this.url;
                                    content += "\">";
                                content += "Pobierz";
                            content += "</a>";
                        }
                        content += "<a href=\"javascript:prepare_comment_dialog('"
                            + this.title + "', "
                            + has_privilege(privilegesStatus, 'comment') + ", "
                            + this.key
                            + ")\">Komentarze</a>";
                    content += "</div>";
                content += "</td>";
            content += "</tr>";
        });
    }
    content += "</table>";
    div.html(content);
    div.find('.resource_group_buttons').buttonset();
    div.find('a, button').button();
}

function create_new_resource_group_button_click() {
    saveMode = "add";
    $('#resource_group_div').dialog("open");
}

function edit_resource_group_button_click(name) {
    saveMode = "edit";
    editModeResourceGroupName = name;
    var description = $('#resource_group_description').html();
    var resource_group_div = $('#resource_group_div');
    resource_group_div.find('#resource_group_name_input').val(name);
    resource_group_div.find('#resource_group_description_textarea').val(description);
    resource_group_div.dialog("open");
}

function delete_resource_group(name) {
    $.ajax({
        type: "DELETE",
        async: false,
        url: rest('/resourceGroups/delete/' + name),
        success: function(result) {
            if(is_success(result)) {
                post_message_now('success', 'Grupa została usunięta');
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd podczas usuwania grupy');
        }
    });
    reload_resource_groups();
}

function show_user_roles_dialog(name, friendlyName) {
    userName = name;
    userFriendlyName = friendlyName;
    var user_roles_div = $('#user_roles_div');
    user_roles_div.find('#user_roles_user_span').html(userFriendlyName);
    user_roles_div.find('#user_roles_group_span').html(groupName);
    $.ajax({
        async: false,
        type: "GET",
        url: rest('/resourceGroups/roles/group/' + groupName + '/user/' + userName),
        success: function(result) {
            show_roles(result);
            user_roles_div.dialog("open");
        },
        error: function() {
            post_message_now('error', 'Błąd przy ładowaniu listy ról dla użytkownika ' + friendlyName);
        }
    });
}

function show_roles(roles) {
    var content = "";
    $.each(roles, function() {
        content += "<input type=\"checkbox\" id=\"" + this.roleName + "\" name=\"" + this.roleName + "\"";
            if(this.has) content += " checked=\"checked\"";
        content += " />";
        content += "<label for=\"" + this.roleName + "\">" + this.roleName + "</label>";
    });
    var user_roles_roles_div = $('#user_roles_roles_div');
    user_roles_roles_div.html(content);
    user_roles_roles_div.buttonset();
}

function prepare_comments(document_id) {
    $.ajax({
        type: "GET",
        url: rest('/comments/document/' + document_id),
        success: function(result) {
            if(is_success(result)) {
                post_comments(result.data, $('#comments_comments_div'));
                $('#comments_div').dialog("open");
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd podczas wczytywania komentarzy');
        }
    });
}

$('#resource_group_div').dialog({
    autoOpen: false,
    width: 'auto',
    height: 'auto',
    modal: true,
    buttons: {
        "Zapisz": function() {
            var resource_group_div = $('#resource_group_div');
            var name = resource_group_div.find('#resource_group_name_input').val();
            var description = resource_group_div.find('#resource_group_description_textarea').val();
            var restUrl = "";
            var method = "";
            if(saveMode == "add") {
                method = "POST";
                restUrl = rest("/resourceGroups/create");
            } else if(saveMode == "edit") {
                method = "PUT";
                restUrl = rest("/resourceGroups/update/" + editModeResourceGroupName);
            }
            var data = get_resource_group_string(name, description);
            $.ajax({
                type: method,
                url: restUrl,
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: data,
                success: function(result) {
                    if(is_success(result)) {
                        post_message_now('success', 'Zapisano grupę ' + name);
                        reload_resource_groups();
                        clear_and_close_resource_group_div();
                    } else {
                        post_error_from_result(result);
                    }
                },
                error: function() {
                    post_message_now('error', 'Błąd podczas zapisywania grupy ' + name);
                }
            })
        },
        "Anuluj": function() {
            clear_and_close_resource_group_div();
        }
    }
});

function prepare_comment_dialog(title, has_privilege, document_id) {
    var comments_div = $('#comments_div');
    comments_div.attr('title', title);

    var buttons = {};

    if(has_privilege) {
        buttons.Zapisz = function() {
            var content = comments_div.find('#comments_textarea').val();
            add_comment(document_id, content, comments_div);
        };
        comments_div.find('#comments_textarea').css('display', 'block');
    }

    buttons.Anuluj = function() {
        clear_and_close_comments_dialog();
    }

    comments_div.dialog({
        autoOpen: false,
        modal: true,
        width: 600,
        height: 400,
        buttons: buttons
    });

    prepare_comments(document_id);
}

$('#user_roles_div').dialog({
    autoOpen: false,
    modal: true,
    width: 'auto',
    height: 'auto',
    buttons: {
        "Zapisz": function() {
            var data = serialize_form('user_roles_form');
            $.ajax({
                type: "PUT",
                url: rest('/resourceGroups/roles/group/' + groupName + '/user/' + userName),
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                data: data,
                success: function(result) {
                    if(is_success(result)) {
                        post_message_now('success', 'Role zostały zaktualizowane');
                        clear_and_close_user_roles_div();
                        load_resource_group_info(activeDiv, groupName);
                    } else {
                        post_error_from_result(result);
                    }
                },
                error: function() {
                    post_message_now('error', 'Błąd przy aktualizacji ról');
                }
            });
        },
        "Anuluj": function() {
            clear_and_close_user_roles_div();
        }
    }
});

function clear_and_close_comments_dialog() {
    var comments_div = $('#comments_div');
    comments_div.dialog('close');
}

function clear_and_close_resource_group_div() {
    var resource_group_div = $('#resource_group_div');
    resource_group_div.find('#resource_group_name_input').val('');
    resource_group_div.find('#resource_group_description_textarea').val('');
    resource_group_div.dialog("close");
}

function clear_and_close_user_roles_div() {
    var user_roles_div = $('#user_roles_div');
    user_roles_div.find('#user_roles_user_span').html('');
    user_roles_div.find('#user_roles_group_span').html('');
    user_roles_div.find('#user_roles_roles_span').html('');
    user_roles_div.dialog("close");
}

function get_resource_group_string(name, description) {
    var resource_group = {
        name: name,
        description: description
    };
    return JSON.stringify(resource_group);
}

function serialize_form(formId) {
    var result = [];
    $('#' + formId + ' :input').each(function() {
        var checkbox = $(this);
        var label = $('label[for='+checkbox.attr('id')+']');
        var entry = {};
        entry['roleName'] = label.text();
        if(checkbox.is(':checked'))
            entry['has'] = true;
        else
            entry['has'] = false;
        result.push(entry);
    });
    return JSON.stringify(result);
}