package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.*;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateDocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.MockEntityValidator;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;

public class DocumentDaoTest extends IdentifableDaoTest<Document, DocumentDao> {
    private DocumentDao documentDao;
    private DocumentFactory factory = new DocumentFactory();
    private DirectoryFactory directoryFactory = new DirectoryFactory();
    private UserFactory userFactory = new UserFactory();
    private MimeTypeFactory mimeTypeFactory = new MimeTypeFactory();

    @Test
    public void testGetSessionFiles() throws Exception {
        User user = userFactory.getSampleEntity();
        Directory directory = directoryFactory.getSampleEntity();
        directory.setOwner(user);
        Document nonSessionDocument = factory.getSampleEntity();
        nonSessionDocument.setOwner(user);
        Document sessionDocument = factory.getSampleEntity();
        sessionDocument.setOwner(user);
        directory.addDocument(nonSessionDocument);
        nonSessionDocument.setDirectory(directory);

        documentDao.persist(sessionDocument);
        documentDao.persist(nonSessionDocument);

        assertThat(documentDao.getSessionDocuments(user)).containsExactly(sessionDocument);
    }

    @Test
    public void testGetRecentDocuments() throws Exception {
        documentDao.findDocumentsNumberUploadRecently(1, new Date());
    }

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
        documentDao = new HibernateDocumentDao(context, sessionFactory, new MockEntityValidator());
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
