package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import java.text.SimpleDateFormat;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.rest;

public class DocumentJsonDto extends FileSystemEntryJsonDto {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    protected String created;
    protected String mime;
    protected String owner;
    protected String url;

    public DocumentJsonDto() {

    }

    public DocumentJsonDto(int id, String name, String stringPath, String created, String mime, String owner,
                           String url) {
        super(id, name, false, stringPath);
        this.created = created;
        this.mime = mime;
        this.owner = owner;
        this.url = url;
    }

    public static DocumentJsonDto from(Document document) {
        if(document == null)
            return new DocumentJsonDto();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return new DocumentJsonDto(document.getId(), document.getName(), document.getStringPath(),
                dateFormatter.format(document.getCreated()), document.getMimeType().getType(),
                document.getOwner().getFriendlyName(), rest("/documents/download/" + document.getId()));
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
