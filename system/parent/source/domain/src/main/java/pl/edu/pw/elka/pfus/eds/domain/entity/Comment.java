package pl.edu.pw.elka.pfus.eds.domain.entity;

import java.util.Date;

/**
 * Klasa reprezentująca komentarz.
 */
public class Comment extends IdentifableEntity implements Versionable {
    private Integer id;
    private String content;
    private Date created;
    private Date modified;
    private Integer version;
    private User user;
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
}