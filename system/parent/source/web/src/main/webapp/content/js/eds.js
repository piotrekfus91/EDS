$(document).ajaxStart(function() {
    $('#ajax_loading').css('display', 'block');
}).ajaxComplete(function() {
    $('#ajax_loading').css('display', 'none');
});
$(document).ready(function() {

});

function make_tabs() {
    var tabs = $('#tabs');
    tabs.tabs({
        heightStyle: 'fill',
        fx: {
            height: 'toggle',
            opacity: 'toggle',
            duration: 5000
        }
    });
    // wylaczamy link z tabów, żeby otworzył się jako czysty GET
    tabs.find('#logout_link').unbind('click');
}

function activate_tab(index) {
    $('#tabs').tabs('option', 'active', index);
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

function has_privilege(privilegeStatus, privilegeName) {
    if(privilegeName in privilegeStatus) {
        return privilegeStatus[privilegeName];
    } else {
        return false;
    }
}

function open_lazy() {
//    console.log("opening");
//    $('.lazy_open').effect(
//        "slide",
//        {},
//        5000
//    );
}