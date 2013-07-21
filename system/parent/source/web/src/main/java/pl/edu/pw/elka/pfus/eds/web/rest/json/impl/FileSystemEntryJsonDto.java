package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import com.google.common.base.CaseFormat;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;

/**
 * DTO na potrzeby eksportu JSON.
 */
public class FileSystemEntryJsonDto {
    private int id;
    private String name;
    private boolean isFolder;

    public FileSystemEntryJsonDto() {

    }

    public FileSystemEntryJsonDto(int id, String name, Class<? extends FileSystemEntry> isFolder) {
        this.id = id;
        this.name = name;
        setIsFolder(isFolder);
    }

    public static FileSystemEntryJsonDto from(FileSystemEntry entry) {
        return new FileSystemEntryJsonDto(entry.getId(), entry.getName(), entry.getClass());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getIsFolder() {
        return isFolder;
    }

    public void setIsFolder(String folder) {
        String lowerUnderscoreName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, folder);
        this.isFolder = "directory".equals(lowerUnderscoreName);
    }

    public void setIsFolder(Class<? extends FileSystemEntry> type) {
        setIsFolder(type.getSimpleName());
    }
}
