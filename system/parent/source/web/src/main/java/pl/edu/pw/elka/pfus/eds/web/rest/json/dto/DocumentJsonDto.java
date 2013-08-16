package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

public class DocumentJsonDto extends FileSystemEntryJsonDto {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private String created;
    private String mime;
    private List<CommentJsonDto> comments = new LinkedList<>();
    private List<TagJsonDto> tags = new LinkedList<>();

    public DocumentJsonDto() {

    }

    public DocumentJsonDto(int id, String name, String stringPath, String created, String mime, List<Comment> comments,
                           List<Tag> tags) {
        super(id, name, false, stringPath);
        this.created = created;
        this.mime = mime;
        for(Comment comment : comments) {
            CommentJsonDto commentDto = CommentJsonDto.from(comment);
            this.comments.add(commentDto);
        }
        for(Tag tag : tags) {
            TagJsonDto tagDto = TagJsonDto.from(tag);
            this.tags.add(tagDto);
        }
    }

    public static DocumentJsonDto from(Document document) {
        if(document == null)
            return new DocumentJsonDto();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return new DocumentJsonDto(document.getId(), document.getName(), document.getStringPath(),
                dateFormatter.format(document.getCreated()), document.getMimeType().getType(), document.getComments(),
                document.getTags());
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
