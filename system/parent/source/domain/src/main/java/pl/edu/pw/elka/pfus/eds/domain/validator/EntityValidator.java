package pl.edu.pw.elka.pfus.eds.domain.validator;

import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;

/**
 * Interfejs dla walidatora encji.
 * Powinien być zaimplementowany w logice i wstrzyknięty
 * do DAO, w celu użycia przed zapisem/aktualizacją.
 */
public interface EntityValidator {
    void validate(GenericEntity entity);
}
