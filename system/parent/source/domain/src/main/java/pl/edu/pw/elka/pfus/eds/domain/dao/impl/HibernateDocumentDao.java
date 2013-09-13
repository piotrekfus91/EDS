package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.Date;
import java.util.List;

public class HibernateDocumentDao extends IdentifableGenericDao<Document> implements DocumentDao {
    private static final String GET_SESSION_DOCUMENTS_QUERY =
            "SELECT d " +
            "FROM Document d " +
                    "JOIN d.owner own " +
            "WHERE own.id = :ownerId " +
                    "AND d.directory IS NULL";

    private static final String WITH_RESOURCE_GROUPS_QUERY =
            "SELECT d " +
                    "FROM Document d " +
                    "LEFT JOIN d.resourceGroups " +
                    "WHERE d.id = :documentId";

    private static final String DOCUMENTS_UPLOADED_RECENTLY_QUERY =
            "SELECT new pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto(" +
                    "day(d.created), " +
                    "month(d.created), " +
                    "year(d.created), " +
                    "COUNT(d)" +
            ") " +
            "FROM Document d " +
            "WHERE d.owner.id = :userId " +
                    "AND d.created >= :fromDate " +
            "GROUP BY day(d.created), " +
                    "month(d.created), " +
                    "year(d.created)";

    private static final String CLEAN_SESSION_DOCUMENTS_QUERY =
            "DELETE " +
            "FROM Document d " +
            "WHERE d.owner.id = :ownerId " +
                    "AND d.sessionDocument = TRUE";

    public HibernateDocumentDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    public HibernateDocumentDao(Session session) {
        super(session);
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
    public Document getDocumentWithGroups(int documentId) {
        Query query = session.createQuery(WITH_RESOURCE_GROUPS_QUERY);
        query.setInteger("documentId", documentId);
        return (Document) query.uniqueResult();
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
    public List<DocumentsNumberInDaysDto> findDocumentsNumberUploadRecently(int userId, Date fromDate) {
        Query query = session.createQuery(DOCUMENTS_UPLOADED_RECENTLY_QUERY);
        query.setInteger("userId", userId);
        query.setDate("fromDate", fromDate);
        return query.list();
    }

    @Override
    protected Class<Document> getEntityClass() {
        return Document.class;
    }
}
