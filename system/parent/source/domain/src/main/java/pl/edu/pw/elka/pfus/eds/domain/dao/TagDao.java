package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

/**
 * DAO dla obiektów {@link Tag}
 */
public interface TagDao extends IdentifableDao<Tag> {
    /**
     * Zwraca tag na podstawie wartości.
     *
     * @param value wartość.
     * @return znaleziony tag.
     */
    Tag findByValue(String value);
}