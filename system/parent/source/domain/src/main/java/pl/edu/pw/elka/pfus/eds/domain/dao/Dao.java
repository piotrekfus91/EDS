package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;

import java.util.List;

/**
 * Interfejs generyczny dla klas pochodnych od GenericEntity.
 * Interfejs powinien być jedyną możliwą drogą dostępu do bazy.
 *
 * @param <T> typ encji.
 */
public interface Dao<T extends GenericEntity> {

    /**
     * Utrwala encję.
     * Nie ma znaczenia czy encja już istnieje (UPDATE) czy nie (INSERT).
     *
     * @param entity encja do utrwalenia.
     */
    void persist(T entity);

    /**
     * Usuwa encję z bazy (DELETE).
     *
     * @param entity encja do usunięcia.
     */
    void delete(T entity);

    /**
     * Zwraca liczbę utrwalonych elementów danego typu.
     *
     * @return liczba elementów.
     */
    int count();

    /**
     * Zwraca listę wszystkich encji danego typu.
     *
     * @return wszystkie encje.
     */
    List<T> getAll();

    /**
     * Zwraca sesję hibernate.
     *
     * @return sesja hibernate.
     */
    Session getSession();
}