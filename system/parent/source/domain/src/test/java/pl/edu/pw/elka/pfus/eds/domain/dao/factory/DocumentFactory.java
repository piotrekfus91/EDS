package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import java.util.Date;

public class DocumentFactory implements EntityFactory<Document> {
    private MimeTypeFactory mimeTypeFactory = new MimeTypeFactory();
    private UserFactory userFactory = new UserFactory();
    private static int counter = 0;

    @Override
    public Document getSampleEntity() {
        Document document = new Document();
        document.setName("name_" + counter++);
        document.setCreated(new Date());
        document.setMimeType(mimeTypeFactory.getSampleEntity());
        document.setOwner(userFactory.getSampleEntity());
        document.setContentMd5("md5");
        return document;
    }
}
