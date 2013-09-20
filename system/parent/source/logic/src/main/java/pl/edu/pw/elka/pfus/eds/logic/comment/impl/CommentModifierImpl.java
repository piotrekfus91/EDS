package pl.edu.pw.elka.pfus.eds.logic.comment.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.CommentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.comment.CommentModifier;
import pl.edu.pw.elka.pfus.eds.logic.exception.DocumentNotExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.Date;

import static pl.edu.pw.elka.pfus.eds.logic.error.handler.ErrorHandler.handle;

public class CommentModifierImpl implements CommentModifier {
    private Context context;
    private SecurityFacade securityFacade;
    private CommentDao commentDao;
    private DocumentDao documentDao;
    private UserDao userDao;

    public CommentModifierImpl(Context context, SecurityFacade securityFacade, CommentDao commentDao,
                               DocumentDao documentDao, UserDao userDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.commentDao = commentDao;
        this.documentDao = documentDao;
        this.userDao = userDao;
    }

    @Override
    public void addComment(int documentId, String content) {
        documentDao.setSession(commentDao.getSession());
        User currentUser = securityFacade.getCurrentUser(context);

        Document document = documentDao.findById(documentId);
        if(document == null)
            throw new DocumentNotExistsException();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreated(new Date());
        comment.setDocument(document);
        comment.setUser(userDao.findById(currentUser.getId()));

        try {
            commentDao.beginTransaction();
            commentDao.persist(comment);
            commentDao.commitTransaction();
        } catch (Exception e) {
            handle(e, commentDao);
            throw new InternalException();
        }
    }
}
