package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.CommentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.EntityValidator;

import java.util.List;

public class HibernateCommentDao extends IdentifableGenericDao<Comment> implements CommentDao {
    private static final String FOR_DOCUMENT_QUERY =
            "SELECT c " +
            "FROM Comment c " +
            "WHERE c.document.id = :documentId " +
            "ORDER BY c.created ASC";

    public HibernateCommentDao(Context context, SessionFactory sessionFactory, EntityValidator validator) {
        super(context, sessionFactory, validator);
    }

    public HibernateCommentDao(Session session, EntityValidator validator) {
        super(session, validator);
    }

    @Override
    public List<Comment> getCommentsForDocument(int documentId) {
        Query query = session.createQuery(FOR_DOCUMENT_QUERY);
        query.setInteger("documentId", documentId);
        return query.list();
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
