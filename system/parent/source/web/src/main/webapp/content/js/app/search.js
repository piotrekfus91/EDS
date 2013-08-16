$(document).ready(function() {
    $('input').button();
    $('#radio_button_set').buttonset();

    $.ajax({
        type: "GET",
        url: rest('/tags/tagCloud'),
        success: function(result) {
            $('#tag_cloud').tagCloud(result);
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu chmury tagów');
        }
    })
});
