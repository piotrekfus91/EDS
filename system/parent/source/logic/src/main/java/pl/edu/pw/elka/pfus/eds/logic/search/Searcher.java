package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Fasada wyszukiwarki.
 */
public interface Searcher {
    /**
     * Wyszukuje wszystkie tagi pasujące po nazwie.
     *
     * @param name nazwa tagu.
     * @return lista tagów pasujących.
     */
    List<Tag> findTagsByName(String name);
}
