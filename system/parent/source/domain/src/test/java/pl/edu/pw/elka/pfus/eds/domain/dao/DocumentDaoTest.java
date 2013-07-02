package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.DocumentFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.MimeTypeFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.UserFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateDocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class DocumentDaoTest extends IdentifableDaoTest<Document, DocumentDao> {
    private DocumentDao documentDao;
    private DocumentFactory factory = new DocumentFactory();
    private UserFactory userFactory = new UserFactory();
    private MimeTypeFactory mimeTypeFactory = new MimeTypeFactory();

    @Override
    public DocumentDao getDao() {
        return documentDao;
    }

    @Override
    public void setDao(DocumentDao dao) {
        this.documentDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        documentDao = new HibernateDocumentDao(context, sessionFactory);
    }

    @Override
    protected EntityFactory<Document> getFactory() {
        return factory;
    }

    @Override
    protected void updateEntity(Document entity) {
        entity.setName(entity.getName() + entity.getName());
        User newUser = userFactory.getSampleEntity();
        newUser.setName("document_test");
        entity.setOwner(newUser);
        MimeType newMimeType = mimeTypeFactory.getSampleEntity();
        newMimeType.setType("document_test");
        entity.setMimeType(newMimeType);
    }
}
