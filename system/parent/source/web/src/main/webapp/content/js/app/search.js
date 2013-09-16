$(document).ready(function() {
    $('input').button();
    $('#radio_button_set').buttonset();
    $('#search').css('display', 'block');

    $('#searcher').click(function() {
        var content_to_search = $('#search_value_input').val();
        if($('#radio_tags').is(':checked'))
            search_tags_by_name(content_to_search);
        else if($('#radio_names').is(':checked'))
            search_file_names(content_to_search);
        $('#radio_button_set').buttonset("refresh");
    });
    open_lazy();
});

function search_tags_by_name(name) {
    $('#search_results').accordion();
    $('#search_value_input').val(name);
    $('#radio_tags').prop('checked', true);
    $.ajax({
        type: "GET",
        url: rest('/search/tags/' + name),
        success: function(result) {
            post_tag_search_results(result);
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu wyników wyszukiwania');
        }
    })
}

function post_tag_search_results(tags) {
    var search_results_div = $('#search_results');
    search_results_div.css('display', 'none');
    search_results_div.accordion("destroy");
    search_results_div.html('');
    $.each(tags, function() {
        append_tag(search_results_div, this);
    });
    search_results_div.accordion({
        collapsible: true,
        heightStyle: "content",
        active: false
    });
    $("h3", "#search_results").click(function() {
        var active_div = $(this).next('div');
        var active_tag = $(this).find("a").attr("title");
        load_tag_info(active_div, active_tag);
    });
    search_results_div.css('display', 'block');
}

function append_tag(search_results_div, tag) {
    var append = "";
    append += "<h3>"
        append += "<a href=\"#\" title=\"" + tag.label + "\">";
            append += tag.label;
        append += "</a>";
    append += "</h3>";
    append += "<div>";
    append += "</div>";
    search_results_div.append(append);
}

function load_tag_info(div, tagName) {
    div.removeAttr('height');
    var append = "";
    div.html('');
    $.ajax({
        type: "GET",
        url: rest("/tags/name/" + tagName),
        async: false,
        success: function(result) {
            append += "<ul>";
            $.each(result.docs, function() {
                append += "<li>";
                    append += "<a href=\"";
                            append += this.url;
                            append += "\">";
                        append += this.title;
                    append += "</a>";
                append += "</li>";
            });
            append += "</ul>";
        }
    });
    div.append(append);
}

function search_file_names(title) {
    if(!title) return;

    $('#search_results').accordion();

    $.ajax({
        type: "GET",
        url: rest("/search/title/" + title),
        async: false,
        success: function(result) {
            post_search_result(title, result.data);
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu wyników wyszukiwania');
        }
    });
}

function post_search_result(title, documents) {
    var search_results_div = $('#search_results');
    search_results_div.css('display', 'none');
    search_results_div.accordion("destroy");
    search_results_div.html('');
    append_search_results(search_results_div, title, documents);
    search_results_div.accordion({
        collapsible: true,
        heightStyle: "content",
        active: true
    });
    $("h3", "#search_results").click(function() {
        var active_div = $(this).next('div');
        var active_tag = $(this).find("a").attr("title");
    });
    search_results_div.css('display', 'block');
}

function append_search_results(search_results_div, title, documents) {
    var append = "";
    append += "<h3>"
        append += "<a href=\"#\" title=\"" + title + "\">";
    append += title;
    append += "</a>";
    append += "</h3>";
    append += "<div>";
        append += "<ul>";
            $.each(documents, function() {
                append += "<li>";
                    append += "<a href=\"#\">"
                        append += this.title;
                    append += "</a>";
                append += "</li>";
            });
        append += "</ul>";
    append += "</div>";
    search_results_div.append(append);
}