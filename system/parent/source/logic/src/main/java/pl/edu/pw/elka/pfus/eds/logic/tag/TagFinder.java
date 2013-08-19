package pl.edu.pw.elka.pfus.eds.logic.tag;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Interfejs do wyszukiwania tagów.
 */
public interface TagFinder {
    /**
     * Zwraca tag z załadowaną listą dokumentów.
     *
     * @param value wartość tagu.
     * @return tag z dokumentami.
     */
    Tag getTagWithLoadedDocuments(String value);

    /**
     * Zwraca listę wszystkich tagów.
     *
     * @return lista wszystkich tagów.
     */
    List<Tag> getAll();

    /**
     * Zwraca listę wszystkich tagów, wraz z przyporządkowanymi im dokumentami.
     *
     * @return lista wszystkich tagów.
     */
    List<Tag> getAllWithLoadedDocuments();

    /**
     * Zwraca listę tagów o podobnej wartości znormalizowanej.
     * Podobność jest ustalana na podstawie dystansu edycyjnego.
     *
     * {@link pl.edu.pw.elka.pfus.eds.util.word.distance.LevenshteinDistance}
     *
     * @param value wartość tagu.
     * @return lista podobnych tagów.
     */
    List<Tag> getSimilars(String value);
}
