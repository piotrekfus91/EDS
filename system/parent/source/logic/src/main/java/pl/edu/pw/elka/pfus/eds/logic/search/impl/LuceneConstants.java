package pl.edu.pw.elka.pfus.eds.logic.search.impl;

/**
 * Stałe dla wyszukiwarki Lucene.
 */
public interface LuceneConstants {
    /**
     * Nazwa pola ID.
     */
    String ID_FIELD = "id";

    /**
     * Nazwa pola tytułu.
     */
    String TITLE_FIELD = "title";

    /**
     * Nazwa pola zawartości dokumentu.
     */
    String CONTENT_FIELD = "content";

    /**
     * Domyślny limit trafień.
     */
    int DEFAULT_HITS_NUMBER = 100;
}
