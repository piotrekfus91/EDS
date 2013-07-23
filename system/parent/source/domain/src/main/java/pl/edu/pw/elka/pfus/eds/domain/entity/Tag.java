package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableSet;
import pl.edu.pw.elka.pfus.eds.util.ValueNormalizer;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Klasa reprezentująca tag dokumentu.
 */
public class Tag extends IdentifableEntity {
    private Integer id;
    private String value;

    /**
     * Nazwa znormalizowana.
     * Jest to nazwa, która nie posiada polskich znaków,
     * zapisana małymi literami i bez spacji, znaków inne niż litery.
     */
    private String normalizedValue;
    public Set<Document> documents = new LinkedHashSet<>();

    /**
     * Zwraca id encji.
     *
     * @return id encji.
     */
    @Override
    public Integer getId() {
        return id;
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

    public Set<Document> getDocuments() {
        return ImmutableSet.copyOf(documents);
    }

    public void setDocuments(Set<Document> documents) {
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