package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.IdentifableEntity;

/**
 * Data Access Object dla obiektów oznaczonych jako Identifable,
 * to znaczy dla takich, których kluczem głównym jest int.
 *
 * @param <T> typ dziedziczący po {@link IdentifableEntity}
 */
public interface IdentifableDao<T extends IdentifableEntity> extends Dao<T> {
    /**
     * Znajduje pojedynczą instancję klasy na podstawie danego id.
     *
     * @param id dane id.
     * @return instancja klasy o danym id.
     */
    T findById(Integer id);
}
