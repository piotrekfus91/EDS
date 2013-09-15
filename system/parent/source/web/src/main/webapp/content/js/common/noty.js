function post_message(type, text, date) {
    var content = date + ':<br />' + text;
    noty({
        type: type,
        text: content
    });
}

function post_message_now(type, text) {
    post_message(type, text, current_time());
}

function post_error_from_result(result) {
    var content = result.error_message;
    var validation_errors = result.validation_errors;
    if(typeof validation_errors !== 'undefined' && validation_errors.length > 0) {
        content += "<ul>";
        $.each(validation_errors, function() {
            content += "<li>";
            content += this;
            content += "</li>";
        });
        content += "</ul>";
    }
    post_message_now('error', content);
}

$.noty.defaults = {
    layout: 'topRight',
    theme: 'defaultTheme',
    type: 'alert',
    text: '',
    dismissQueue: true, // If you want to use queue feature set this true
    template: '<div class="noty_message"><span class="noty_text"></span><div class="noty_close"></div></div>',
    animation: {
        open: {height: 'toggle'},
        close: {height: 'toggle'},
        easing: 'swing',
        speed: 500 // opening & closing animation speed
    },
    timeout: 5000, // delay for closing event. Set false for sticky notifications
    force: false, // adds notification to the beginning of queue when set to true
    modal: false,
    closeWith: ['click'], // ['click', 'button', 'hover']
    callback: {
        onShow: function() {},
        afterShow: function() {},
        onClose: function() {},
        afterClose: function() {}
    },
    buttons: false // an array of buttons
};