package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

public class DirectoryJsonDto extends FileSystemEntryJsonDto {
    public DirectoryJsonDto() {

    }

    public DirectoryJsonDto(int id, String name) {
        super(id, name, true);
    }

    public static DirectoryJsonDto from(Directory directory) {
        return new DirectoryJsonDto(directory.getId(), directory.getName());
    }

    @Override
    public boolean getFolder() {
        return true;
    }

    public boolean getIsLazy() {
        return true;
    }
}
