$(document).ready(function() {
    $.ajax({
        type: "GET",
        url: rest('/tags/tagCloud'),
        success: function(result) {
            $('#tag_cloud').tagCloud(result, {
                click: function(tagName) {
                    search_tags_by_name(tagName);
                }
            });
        },
        error: function() {
            post_message_now('error', 'Błąd przy wczytywaniu chmury tagów');
        }
    })
});