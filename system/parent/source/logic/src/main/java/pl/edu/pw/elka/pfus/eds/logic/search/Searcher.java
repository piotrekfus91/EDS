package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;

import java.util.List;

/**
 * Interfejs dający możliwość przeszukiwania indeksu.
 */
public interface Searcher {
    /**
     * Zwraca listę dokumentów pasujących do tytułu.
     *
     * @param title tytuł.
     * @return lista dokumentów pasujących do tytułu.
     */
    List<DocumentSearchDto> findByTitle(String title);

    /**
     * Zwraca listę dokumentów pasujących do zawartości.
     *
     * @param content zawartość.
     * @return lista dokumentów pasujących do zawartości.
     */
    List<DocumentSearchDto> findByContent(String content);
}
