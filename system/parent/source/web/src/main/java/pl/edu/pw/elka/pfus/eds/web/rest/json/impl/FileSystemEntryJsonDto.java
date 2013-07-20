package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import com.google.common.base.CaseFormat;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;

/**
 * DTO na potrzeby eksportu JSON.
 */
public class FileSystemEntryJsonDto {
    private int id;
    private String name;
    private String type;

    public FileSystemEntryJsonDto() {

    }

    public FileSystemEntryJsonDto(int id, String name, Class<? extends FileSystemEntry> type) {
        this.id = id;
        this.name = name;
        setType(type);
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, type);
    }

    public void setType(Class<? extends FileSystemEntry> type) {
        setType(type.getSimpleName());
    }
}
