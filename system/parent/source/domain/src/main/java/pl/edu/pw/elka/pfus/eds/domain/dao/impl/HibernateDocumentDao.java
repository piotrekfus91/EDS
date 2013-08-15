package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.List;

public class HibernateDocumentDao extends IdentifableGenericDao<Document> implements DocumentDao {
    private static final String GET_SESSION_DOCUMENTS_QUERY =
            "SELECT d " +
            "FROM Document d " +
                    "JOIN d.owner own " +
            "WHERE own.id = :ownerId " +
                    "AND d.directory IS NULL";

    private static final String CLEAN_SESSION_DOCUMENTS_QUERY =
            "DELETE " +
            "FROM Document d " +
            "WHERE d.owner.id = :ownerId " +
                    "AND d.sessionDocument = TRUE";

    public HibernateDocumentDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    public List<Document> getSessionDocuments(User user) {
        return getSessionDocuments(user.getId());
    }

    @Override
    public List<Document> getSessionDocuments(int userId) {
        Query query = session.createQuery(GET_SESSION_DOCUMENTS_QUERY);
        query.setInteger("ownerId", userId);
        return query.list();
    }

    @Override
    public void deleteSessionDocuments(User user) {
        cleanSessionDocuments(user.getId());
    }

    @Override
    public void cleanSessionDocuments(int userId) {
        Query query = session.createQuery(CLEAN_SESSION_DOCUMENTS_QUERY);
        query.setInteger("ownerId", userId);
        query.executeUpdate();
    }

    @Override
    protected Class<Document> getEntityClass() {
        return Document.class;
    }
}
