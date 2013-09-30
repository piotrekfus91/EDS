$(document).ready(function() {
    var uploader = $('#file_upload').plupload({
        runtimes: 'html5',
        url: rest('/upload'),
        unique_names: true
    });

    uploader.bind('Error', function(up, err) {
        post_message_now('error', err.message);
    });
});

