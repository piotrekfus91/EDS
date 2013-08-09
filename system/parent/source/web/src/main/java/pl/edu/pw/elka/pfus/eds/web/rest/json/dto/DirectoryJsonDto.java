package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;

public class DirectoryJsonDto extends FileSystemEntryJsonDto {
    public DirectoryJsonDto() {

    }

    public DirectoryJsonDto(int id, String name, String stringPath) {
        super(id, name, true, stringPath);
    }

    public static DirectoryJsonDto from(Directory directory) {
        if(directory == null)
            return new DirectoryJsonDto();
        return new DirectoryJsonDto(directory.getId(), directory.getName(), directory.getStringPath());
    }

    @Override
    public boolean getFolder() {
        return true;
    }

    public boolean getIsLazy() {
        return true;
    }
}
