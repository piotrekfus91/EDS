package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.hibernate.validator.constraints.Length;
import pl.edu.pw.elka.pfus.eds.util.ValueNormalizer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Klasa reprezentująca tag dokumentu.
 */
public class Tag extends IdentifableEntity {
    private Integer id;
    @Length(min = 1, max = 30, message = "{tag.length}")
    private String value;

    /**
     * Nazwa znormalizowana.
     * Jest to nazwa, która nie posiada polskich znaków,
     * zapisana małymi literami i bez spacji, znaków inne niż litery.
     */
    private String normalizedValue;
    private Integer version;
    public List<Document> documents = new LinkedList<>();

    public static Tag from(Tag tag) {
        Tag newTag = new Tag();
        newTag.setId(tag.getId());
        newTag.setValue(tag.getValue());
        newTag.setDocuments(Lists.newLinkedList(tag.getDocuments()));
        return newTag;
    }

    /**
     * Zwraca id encji.
     *
     * @return id encji.
     */
    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void removeFromAssociations() {
        documents.remove(this);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
        normalizedValue = ValueNormalizer.normalizeValue(value);
    }

    public String getNormalizedValue() {
        return normalizedValue;
    }

    public void setNormalizedValue(String normalizedValue) {
        this.normalizedValue = normalizedValue;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public List<Document> getDocuments() {
        documents.removeAll(Collections.singleton(null));
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

        Tag tag = (Tag) o;

        if (value != null ? !value.equals(tag.value) : tag.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }
}