package pl.edu.pw.elka.pfus.eds.domain.dao;

import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;
import pl.edu.pw.elka.pfus.eds.domain.entity.Named;

public interface NamedDao<T extends GenericEntity & Named> extends Dao<T> {
    /**
     * Zwraca pojedynczą instancję klasy typu Named.
     *
     * @param name nazwa po której wyszukujemy.
     * @return instancja Named.
     */
    T findByName(String name);
}
