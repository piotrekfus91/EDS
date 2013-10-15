package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Klasa reprezentująca komentarz.
 */
public class Comment extends IdentifableEntity {
    private Integer id;

    @Length(min=1, message = "{comment.not.empty}")
    private String content;

    @NotNull(message = "{creation.date.not.empty}")
    private Date created;

    private Date modified;
    private Integer version;

    @NotNull(message = "{comment.user.not.empty}")
    private User user;

    @NotNull(message = "{comment.document.not.empty}")
    private Document document;

    /**
     * Zwraca id encji.
     *
     * @return id encji.
     */
    @Override
    public Integer getId() {
        return id;
    }

    /**
     * Zwraca wersję encji na potrzeby optymistycznego blokowania.
     *
     * @return wersja.
     */
    @Override
    public Integer getVersion() {
        return version;
    }

    @Override
    public void removeFromAssociations() {
        // brak kolekcji
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (content != null ? !content.equals(comment.content) : comment.content != null) return false;
        if (document != null ? !document.equals(comment.document) : comment.document != null) return false;
        if (user != null ? !user.equals(comment.user) : comment.user != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(content, document, user);
    }
}