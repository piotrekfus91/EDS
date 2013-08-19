$(document).ajaxStart(function() {
    $('#ajax_loading').css('display', 'block');
}).ajaxComplete(function() {
    $('#ajax_loading').css('display', 'none');
});
$(document).ready(function() {

});

function animate_page() {
    $('body').hide().effect(
        'slide',
        {
            direction: "down"
        },
        1000
    );
}

function make_tabs() {
    var tabs = $('#tabs');
    tabs.tabs();
    // wylaczamy link z tabów, żeby otworzył się jako czysty GET
    tabs.find('#logout_link').unbind('click');
}

function activate_tab(href) {
    var index = $('#tabs a[href="' + href + '"]').parent().index();
    $('#tabs').tabs('option', 'active', index-1);
}

function current_time() {
    var current_date = new Date();
    return current_date.getHours() + ":" + current_date.getMinutes() + ":" + current_date.getSeconds();
}

function rest(suffix) {
    return '/rest' + suffix;
}

function is_success(result) {
    return result.result == "SUCCESS";
}