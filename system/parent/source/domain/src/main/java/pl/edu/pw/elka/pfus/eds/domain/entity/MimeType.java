package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import org.hibernate.validator.constraints.Length;

import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca dozwolone typy MIME.
 */
public class MimeType extends IdentifableEntity {
    private Integer id;
    @Length(min = 1, max = 30, message = "{mime.type.type.length}")
    private String type;

    private boolean enabled = true;
    private String defaultExtension;
    private String description;
    private Integer version;
    private List<Document> documents = new LinkedList<>();

    /**
     *Zwraca id encji.
     *
     * @return id encji.
     */
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void removeFromAssociations() {
        // zagwarantowane przez cascade
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDefaultExtension() {
        return defaultExtension;
    }

    public void setDefaultExtension(String defaultExtension) {
        this.defaultExtension = defaultExtension;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Document> getDocuments() {
        return ImmutableList.copyOf(documents);
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MimeType mimeType = (MimeType) o;

        if (type != null ? !type.equals(mimeType.type) : mimeType.type != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(type);
    }
}