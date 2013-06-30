package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;
import pl.edu.pw.elka.pfus.eds.domain.entity.Named;

/**
 * Data Access Object dla obiektów, które da się jednoznacznie zidentyfikować po nazwie.
 *
 * @param <T> typ będący encją ({@link GenericEntity}) i implementujący {@link Named}.
 */
public interface NamedDao<T extends GenericEntity & Named> extends Dao<T> {
    /**
     * Zwraca pojedynczą instancję klasy typu Named.
     *
     * @param name nazwa po której wyszukujemy.
     * @return instancja Named.
     */
    T findByName(String name);
}
