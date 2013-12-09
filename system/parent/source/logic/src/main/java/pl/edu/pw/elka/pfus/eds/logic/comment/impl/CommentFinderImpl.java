package pl.edu.pw.elka.pfus.eds.logic.comment.impl;

import pl.edu.pw.elka.pfus.eds.domain.dao.CommentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentFinder;

import java.util.List;

public class CommentFinderImpl implements CommentFinder {
    private CommentDao commentDao;

    public CommentFinderImpl(CommentDao commentDao) {
        this.commentDao = commentDao;
    }

    @Override
    public List<Comment> getCommentsForDocument(int documentId) {
        commentDao.clear();
        return commentDao.getCommentsForDocument(documentId);
    }
}
