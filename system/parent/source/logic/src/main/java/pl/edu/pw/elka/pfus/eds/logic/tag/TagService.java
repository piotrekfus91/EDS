package pl.edu.pw.elka.pfus.eds.logic.tag;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Fasada dla tagów.
 */
public interface TagService extends TagFinder {
    /**
     * Zwraca listę tagów.
     *
     * @return lista tagów.
     */
    List<Tag> getAll();
}
