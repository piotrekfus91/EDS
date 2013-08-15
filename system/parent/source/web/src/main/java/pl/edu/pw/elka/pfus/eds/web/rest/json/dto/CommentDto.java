package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.Comment;

import java.text.SimpleDateFormat;

public class CommentDto {
    private static final String DATE_FORMAT = "dd-MM-yyyy";

    private int id;
    private String content;
    private String created;
    private String user;

    public CommentDto() {

    }

    public CommentDto(int id, String content, String created, String user) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.user = user;
    }

    public static CommentDto from(Comment comment) {
        if(comment == null)
            return new CommentDto();
        SimpleDateFormat dateFormatter = new SimpleDateFormat();
        return new CommentDto(comment.getId(), comment.getContent(), dateFormatter.format(comment.getCreated()),
                comment.getUser().getFriendlyName());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
