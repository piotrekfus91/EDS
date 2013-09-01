package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.CommentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateCommentDao extends IdentifableGenericDao<Comment> implements CommentDao {
    public HibernateCommentDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    public HibernateCommentDao(Session session) {
        super(session);
    }

    @Override
    protected Class<Comment> getEntityClass() {
        return Comment.class;
    }
}
