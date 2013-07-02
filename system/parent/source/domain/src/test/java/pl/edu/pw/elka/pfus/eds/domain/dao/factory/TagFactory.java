package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

public class TagFactory implements EntityFactory<Tag> {
    private static int counter = 0;

    @Override
    public Tag getSampleEntity() {
        Tag tag = new Tag();
        tag.setValue("sample value" + counter++);
        return tag;
    }
}
