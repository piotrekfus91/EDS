var saveMode = ""; // add lub edit
var editModeResourceGroupName = "";
var userFriendlyName = "";
var userName;
var groupName = "";
var activeDiv;
var privilegesStatus = {};

$(document).ready(function() {
    $('#resource_groups').accordion();
    $('#create_new_resource_group_button').button().click(create_new_resource_group_button_click);

    reload_resource_groups();
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
    run_template('ResourceGroups', resource_groups_div, {
        resource_groups: resource_groups
    });
    console.log(resource_groups_div.html());
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
    groupName = data.resourceGroup.name;
    privilegesStatus = data.privilegesStatus;
    div.removeAttr('height');
    run_template('ResourceGroup', div, {
        resource_group: data.resourceGroup,
        users: data.users,
        manage_roles: has_privilege(privilegesStatus, 'manage_roles'),
        update_info: has_privilege(privilegesStatus, 'update_info'),
        delete: has_privilege(privilegesStatus, 'delete'),
        list_files: has_privilege(privilegesStatus, 'list_files'),
        download_files: has_privilege(privilegesStatus, 'download_files'),
        comment: has_privilege(privilegesStatus, 'comment'),
        comments_div: 'comments_div'
    });
    div.find('.resource_group_buttons').buttonset();
    div.find('a, button').button();
}

function create_new_resource_group_button_click() {
    saveMode = "add";
    var resource_group_div = $('#resource_group_div');
    resource_group_div.find('#resource_group_name_input').val('');
    resource_group_div.find('#resource_group_description_textarea').val('');
    resource_group_div.dialog("open");
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

$('#resource_group_div').dialog(
    $.extend({}, dialogDefaults, {
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
    })
);

$('#user_roles_div').dialog(
    $.extend({}, dialogDefaults, {
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
    })
);

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