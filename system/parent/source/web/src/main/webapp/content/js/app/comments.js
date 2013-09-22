function post_comments(comments, div) {
    run_template('Comments', div, {
        comments: comments
    });
}

function add_comment(documentId, content, div) {
    $.ajax({
        type: "POST",
        url: rest('/comments/create/' + documentId),
        data: content,
        success: function(result) {
            if(is_success(result)) {
                post_message_now('success', 'Komentarz dodany');
                if(typeof div !== 'undefined')
                    div.dialog("close");
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd przy dodawaniu komentarza');
        }
    });
}

function prepare_comment_dialog(title, has_privilege, document_id, comments_div_id) {
    var comments_div = $('#' + comments_div_id);
    comments_div.attr('title', title);

    var buttons = {};

    comments_div.find('#comments_textarea').val('');
    if(has_privilege) {
        buttons.Zapisz = function() {
            var content = comments_div.find('#comments_textarea').val();
            add_comment(document_id, content, comments_div);
        };
    }

    buttons.Anuluj = function() {
        clear_and_close_comments_dialog();
    }

    comments_div.dialog(
        $.extend({}, dialogDefaults, {
            width: 600,
            height: 400,
            buttons: buttons
        })
    );

    prepare_comments(document_id);
}

function prepare_comments(document_id) {
    $.ajax({
        type: "GET",
        url: rest('/comments/document/' + document_id),
        success: function(result) {
            if(is_success(result)) {
                post_comments(result.data, $('#comments_comments_div'));
                $('#comments_div').dialog("open");
            } else {
                post_error_from_result(result);
            }
        },
        error: function() {
            post_message_now('error', 'Błąd podczas wczytywania komentarzy');
        }
    });
}

function clear_and_close_comments_dialog() {
    var comments_div = $('#comments_div');
    comments_div.dialog('close');
}