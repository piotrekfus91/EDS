package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

public class DirectoryFactory implements EntityFactory<Directory> {
    @Override
    public Directory getSampleEntity() {
        Directory directory = new Directory();
        directory.setName("name");
        return directory;
    }
}
