$(document).ready(function() {
    $('input').button();
    $('#radio_button_set').buttonset();
    $('#search').css('display', 'block');

    $('#searcher').click(function() {
        var value_to_search = $('#search_value_input').val();
        if($('#radio_tags').is(':checked'))
            search_tags_by_name(value_to_search);
        else if($('#radio_names').is(':checked'))
            search_files(value_to_search, 'title');
        else if($('#radio_content').is(':checked'))
            search_files(value_to_search, 'content');
    });
});

function search_tags_by_name(name) {
    $('#search_results').accordion();
    $('#search_value_input').val(name);
    $('#radio_tags').prop('checked', true);
    $('#radio_button_set').buttonset("refresh");
    $.ajax({
        type: "GET",
        url: rest('/search/tags/' + name),
        success: function(result) {
            post_tag_search_results(result);
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu wyników wyszukiwania');
        }
    });
}

function post_tag_search_results(tags) {
    var search_results_div = $('#search_results');
    search_results_div.css('display', 'none');
    search_results_div.accordion("destroy");
    search_results_div.html('');
    append_tags(search_results_div, tags);
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

function append_tags(search_results_div, tags) {
    run_template('Tag', search_results_div, {
        tags: tags
    });
}

function load_tag_info(div, tagName) {
    div.removeAttr('height');
    $.ajax({
        type: "GET",
        url: rest("/tags/name/" + tagName),
        async: false,
        success: function(result) {
            run_template('TagSearches', div, {
                search_results: result.docs
            });
        }
    });
}

function search_files(value, where) {
    if(!value) return;

    $('#search_results').accordion();

    $.ajax({
        type: "GET",
        url: rest("/search/" + where + "/" + value),
        async: false,
        success: function(result) {
            if(is_success(result))
                post_search_result(value, result.data);
            else
                post_error_from_result(result);
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
        collapsible: false,
        heightStyle: "content",
        active: true
    });
    search_results_div.css('display', 'block');
}

function append_search_results(search_results_div, title, documents) {
    run_template('DocumentSearches', search_results_div, {
        title: title,
        documents: documents
    });
}