function post_comments(comments, div) {
    div.html('');
    $.each(comments, function(index) {
        var comment_div_class = index % 2 == 0 ? 'files_comment_even' : 'files_comment_odd';
        var commentBody = "";
        commentBody += "<div class=\"" + comment_div_class + "\">";
            commentBody += "<div class=\"files_comment_content\">";
                commentBody += this.content;
            commentBody += "</div>";
            commentBody += "<div class=\"files_comment_footer\">";
                commentBody += "<span class=\"files_comment_author\">";
                    commentBody += this.user;
                commentBody += "</span>,&nbsp;";
                commentBody += "<span class=\"files_comment_date\">";
                    commentBody += this.created;
                commentBody += "</span>";
            commentBody += "</div>";
        commentBody += "</div>";
        div.append(commentBody);
    });
}