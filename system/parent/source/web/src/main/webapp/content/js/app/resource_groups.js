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
    resource_groups_div.css('display', 'block');
}

function append_resource_group(div, resource_group) {
    var content = "";
    content += "<h3>";
        content += resource_group.name;
    content += "</h3>";
    content += "<div>";
        content += "tu bedzie cos z RG";
    content += "</div>";
    div.append(content);
}