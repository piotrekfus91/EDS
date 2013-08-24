$(document).ready(function() {
    $('#resource_groups').accordion();

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
    content += "Dostępne dokumenty";
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
}