package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;

public class MimeTypeFactory implements EntityFactory<MimeType> {
    private static int counter = 0;

    @Override
    public MimeType getSampleEntity() {
        MimeType mimeType = new MimeType();
        mimeType.setType("type_" + counter++);
        mimeType.setDefaultExtension("ext");
        mimeType.setDescription("");
        return mimeType;
    }
}
