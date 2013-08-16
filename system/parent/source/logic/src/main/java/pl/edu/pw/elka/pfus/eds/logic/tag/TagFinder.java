package pl.edu.pw.elka.pfus.eds.logic.tag;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Interfejs do wyszukiwania tagów.
 */
public interface TagFinder {
    List<Tag> getAll();
}
