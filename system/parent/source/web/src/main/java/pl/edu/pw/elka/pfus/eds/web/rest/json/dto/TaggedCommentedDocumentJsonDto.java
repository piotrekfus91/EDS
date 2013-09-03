package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import static pl.edu.pw.elka.pfus.eds.web.rest.Rest.rest;

public class TaggedCommentedDocumentJsonDto extends DocumentJsonDto {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private List<CommentJsonDto> comments = new LinkedList<>();
    private List<TagJsonDto> tags = new LinkedList<>();

    public TaggedCommentedDocumentJsonDto() {

    }

    public TaggedCommentedDocumentJsonDto(int id, String name, String stringPath, String created, String mime,
                                          List<Comment> comments, List<Tag> tags, String owner, String url) {
        super(id, name, stringPath, created, mime, owner, url);
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

    public static TaggedCommentedDocumentJsonDto from(Document document) {
        if(document == null)
            return new TaggedCommentedDocumentJsonDto();
        SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMAT);
        return new TaggedCommentedDocumentJsonDto(document.getId(), document.getName(), document.getStringPath(),
                dateFormatter.format(document.getCreated()), document.getMimeType().getType(), document.getComments(),
                document.getTags(), document.getOwner().getFriendlyName(),
                rest("/documents/download/" + document.getId()));
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
