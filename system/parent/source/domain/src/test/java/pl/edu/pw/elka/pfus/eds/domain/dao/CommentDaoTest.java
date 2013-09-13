package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.*;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateCommentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.MockEntityValidator;

public class CommentDaoTest extends IdentifableDaoTest<Comment, CommentDao> {
    private static int counter = 0;
    private CommentDao commentDao;
    private CommentFactory factory = new CommentFactory();
    private UserFactory userFactory = new UserFactory();
    private DocumentFactory documentFactory = new DocumentFactory();
    private MimeTypeFactory mimeTypeFactory = new MimeTypeFactory();

    @Override
    public CommentDao getDao() {
        return commentDao;
    }

    @Override
    public void setDao(CommentDao dao) {
        this.commentDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        commentDao = new HibernateCommentDao(context, sessionFactory, new MockEntityValidator());
    }

    @Override
    protected EntityFactory<Comment> getFactory() {
        return factory;
    }

    @Override
    protected void updateEntity(Comment entity) {
        entity.setContent(entity.getContent() + entity.getContent());
        User newUser = userFactory.getSampleEntity();
        newUser.setName("comment_test_" + counter++);
        entity.setUser(newUser);
        Document newDocument = documentFactory.getSampleEntity();
        newDocument.setName("comment_test");
        entity.setDocument(newDocument);
        MimeType newMimeType = mimeTypeFactory.getSampleEntity();
        newMimeType.setType("comment_test");
        newDocument.setMimeType(newMimeType);
    }
}
