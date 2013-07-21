package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import com.google.common.base.CaseFormat;
import com.google.gson.annotations.SerializedName;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;

/**
 * DTO na potrzeby eksportu JSON.
 */
public class FileSystemEntryJsonDto {
    @SerializedName("key")
    protected int id;

    @SerializedName("title")
    protected String name;

    @SerializedName("isFolder")
    protected boolean folder;

    @SerializedName("isLazy")
    protected boolean lazy = true;

    public FileSystemEntryJsonDto() {

    }

    public FileSystemEntryJsonDto(int id, String name, Class<? extends FileSystemEntry> folder) {
        this.id = id;
        this.name = name;
        setFolder(folder);
    }

    public FileSystemEntryJsonDto(int id, String name, boolean folder) {
        this.id = id;
        this.name = name;
        this.folder = folder;
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

    public boolean getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        String lowerUnderscoreName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, folder);
        this.folder = "directory".equals(lowerUnderscoreName);
    }

    public void setFolder(Class<? extends FileSystemEntry> type) {
        setFolder(type.getSimpleName());
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
    }

    public boolean isLazy() {
        return lazy;
    }

    public void setLazy(boolean lazy) {
        this.lazy = lazy;
    }
}
