package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import java.text.SimpleDateFormat;

public class DocumentJsonDto extends FileSystemEntryJsonDto {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private String created;
    private String mime;

    public DocumentJsonDto() {

    }

    public DocumentJsonDto(int id, String name, String stringPath, String created, String mime) {
        super(id, name, false, stringPath);
        this.created = created;
        this.mime = mime;
    }

    public static DocumentJsonDto from(Document document) {
        if(document == null)
            return new DocumentJsonDto();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return new DocumentJsonDto(document.getId(), document.getName(), document.getStringPath(),
                dateFormatter.format(document.getCreated()), document.getMimeType().getType());
    }

    @Override
    public boolean getFolder() {
        return false;
    }

    public boolean getIsLazy() {
        return false;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getMime() {
        return mime;
    }

    public void setMime(String mime) {
        this.mime = mime;
    }
}
