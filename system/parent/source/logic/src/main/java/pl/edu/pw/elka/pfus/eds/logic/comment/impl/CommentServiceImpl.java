package pl.edu.pw.elka.pfus.eds.logic.comment.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentFinder;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentModifier;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentService;

import java.util.List;

public class CommentServiceImpl implements CommentService {
    private CommentFinder commentFinder;
    private CommentModifier commentModifier;

    public CommentServiceImpl(CommentFinder commentFinder, CommentModifier commentModifier) {
        this.commentFinder = commentFinder;
        this.commentModifier = commentModifier;
    }

    @Override
    public List<Comment> getCommentsForDocument(int documentId) {
        return commentFinder.getCommentsForDocument(documentId);
    }
}
