function animate_page() {
    $('body').hide().effect(
        'slide',
        {
            direction: "down"
        },
        2000
    );
}

function make_tabs() {
    $('#tabs').tabs();
    // wylaczamy link z tabów, żeby otworzył się jako czysty GET
    $('#logout_link').unbind('click');
}