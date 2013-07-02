package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.IdentifableEntity;

public interface EntityFactory<T extends IdentifableEntity> {
    T getSampleEntity();
}
