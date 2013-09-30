package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Intefejs dla wyszukiwania tagów.
 */
public interface TagSearcher {
    /**
     * Wyszukuje wszystkie tagi pasujące po nazwie.
     *
     * @param name nazwa tagu.
     * @return lista tagów pasujących.
     */
    List<Tag> findTagsByName(String name);
}
