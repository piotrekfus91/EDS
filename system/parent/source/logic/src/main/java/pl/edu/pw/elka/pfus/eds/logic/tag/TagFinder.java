package pl.edu.pw.elka.pfus.eds.logic.tag;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Interfejs do wyszukiwania tagów.
 */
public interface TagFinder {
    /**
     * Zwraca listę wszystkich tagów.
     *
     * @return lista wszystkich tagów.
     */
    List<Tag> getAll();

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
