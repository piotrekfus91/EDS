package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

public class DirectoryFactory implements EntityFactory<Directory> {
    private UserFactory userFactory = new UserFactory();

    @Override
    public Directory getSampleEntity() {
        Directory directory = new Directory();
        directory.setName("name");
        directory.setOwner(userFactory.getSampleEntity());
        return directory;
    }
}
