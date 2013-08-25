var saveMode = ""; // add lub edit
var editModeResourceGroupName = "";

$(document).ready(function() {
    $('#resource_groups').accordion();
    $('#create_new_resource_group_button').button();
    $('#create_new_resource_group_button').click(create_new_resource_group_button_click);

    reload_resource_groups();
});

function reload_resource_groups() {
    $.ajax({
        type: "GET",
        url: rest("/resourceGroups/founded"),
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
        var active_div = $(this).next('div');
        var active_resource_group = $(this).find("a").attr("title");
        load_resource_group_info(active_div, active_resource_group);
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

function post_resource_group_info(div, resource_group) {
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
        content += "<button onclick=\"javascript:edit_resource_group_button_click('" + resource_group.name + "')\">";
            content += "Edytuj informacje o grupie";
        content += "</button>";
        content += "<button onclick=\"javascript:delete_resource_group('" + resource_group.name + "')\">";
            content += "Usuń grupę zasobów";
        content += "</button>";
    content += "</div>";
    content += "<br />";
    content += "<table class=\"resource_group_documents_table\">";
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
                content += "<a href=\"#\">";
                    content += "Pobierz";
                content += "</a>";
            content += "</td>";
        content += "</tr>";
    });
    content += "</table>";
    div.html(content);
    div.find('#resource_group_buttons').buttonset();
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
            var data = create_resource_group(name, description);
            console.log(name);
            console.log(description);
            console.log(data);
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

function clear_and_close_resource_group_div() {
    var resource_group_div = $('#resource_group_div');
    resource_group_div.find('#resource_group_name_input').val('');
    resource_group_div.find('#resource_group_description_textarea').val('');
    resource_group_div.dialog("close");
}

function create_resource_group(name, description) {
    var resource_group = {
        name: name,
        description: description
    };
    return JSON.stringify(resource_group);
}