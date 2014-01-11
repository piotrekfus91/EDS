$(document).ajaxStart(function() {
    $('#ajax_loading').css('display', 'block');
}).ajaxComplete(function() {
    $('#ajax_loading').css('display', 'none');
});
$(document).ready(function() {

});

var dialogDefaults = {
    autoOpen: false,
    modal: true,
    width: 'auto',
    height: 'auto',
    show: {
        effect: 'blind',
        duration: 500
    },
    hide: {
        effect: 'blind',
        duration: 500
    }
};

function make_tabs() {
    var tabs = $('#tabs');
    tabs.tabs({
        heightStyle: 'fill'
    });
    // wylaczamy link z tabów, żeby otworzył się jako czysty GET
    tabs.find('#logout_link').unbind('click');
}

Handlebars.registerHelper("moduloIf", function(index_count,mod,block) {
    if(parseInt(index_count)%(mod)=== 0){
        return block.fn(this);
    }
});

function run_template(templateName, div, context) {
    div.html('');
    $.ajax({
        type: "GET",
        url: 'eds/v/app.templates.' + templateName,
        async: false,
        success: function(source) {
            var template = Handlebars.compile(source);
            var html = template(context);
            div.html(html);
        },
        error: function() {
            post_message_now('error', 'Błąd wewnętrzny');
        }
    });
}

function activate_tab(index) {
    $('#tabs').tabs('option', 'active', index);
}

function current_time() {
    var current_date = new Date();
    return current_date.getHours() + ":" + current_date.getMinutes() + ":" + current_date.getSeconds();
}

function rest(suffix) {
    return '/eds/rest' + suffix;
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