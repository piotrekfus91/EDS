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