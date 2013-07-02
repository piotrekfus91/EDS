package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateDocumentDao extends IdentifableGenericDao<Document> implements DocumentDao {
    public HibernateDocumentDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    protected Class<Document> getEntityClass() {
        return Document.class;
    }
}
