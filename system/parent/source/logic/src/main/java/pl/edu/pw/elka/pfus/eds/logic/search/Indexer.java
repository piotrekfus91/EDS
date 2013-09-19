package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import java.io.IOException;

/**
 * Interfejs indeksera dokumentów.
 * Odpowiada za wszelkie modyfikacje indeksu, dodawanie,
 * modyfikacje, usuwanie.
 */
public interface Indexer {
    /**
     * Indeksuje dokument.
     *
     * @param document dokument do indeksu.
     * @throws IOException przy błędzie dostępu do plików indeksu.
     */
    void index(Document document) throws IOException;

    /**
     * Aktualizuje indeks po zmianie nazwy dokumentu.
     *
     * @param document dokument do indeksowania.
     * @throws IOException przy błędzie dostępu do plików indeksu.
     */
    void updateIndex(Document document) throws IOException;

    /**
     * Usuwa z indeksu wpis o dokumencie o podanym id.
     *
     * @param documentId id dokumentu.
     * @throws IOException przy błędzie dostępu do plików indeksu.
     */
    void deleteFromIndex(int documentId) throws IOException;
}
