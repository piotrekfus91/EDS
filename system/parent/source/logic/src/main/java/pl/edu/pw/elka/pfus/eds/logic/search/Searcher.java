package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;

import java.util.List;

public interface Searcher {
    List<DocumentSearchDto> findByTitle(String title);
}
