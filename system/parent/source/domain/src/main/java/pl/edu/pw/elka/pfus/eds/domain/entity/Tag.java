package pl.edu.pw.elka.pfus.eds.domain.entity;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
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
     * zapisana małymi literami i bez spacji.
     */
    private String normalizedValue;
    public Set<Document> documents = new HashSet<Document>();

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
    }

    public String getNormalizedValue() {
        return normalizedValue;
    }

    // TODO metoda wyliczajaca znormalizowana wartosc

    public Set<Document> getDocuments() {
        return ImmutableSet.copyOf(documents);
    }

    public void addDocument(Document document) {
        documents.add(document);
    }

    public void removeDocument(Document document) {
        documents.remove(document);
    }
}