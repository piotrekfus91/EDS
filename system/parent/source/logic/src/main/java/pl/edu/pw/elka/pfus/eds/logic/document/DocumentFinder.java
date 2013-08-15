package pl.edu.pw.elka.pfus.eds.logic.document;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

/**
 * Interfejs odpowiedzialny za dostarczanie informacji o dokumentach.
 */
public interface DocumentFinder {
    /**
     * Zwraca podany dokument na podstawie danego identyfikatora.
     *
     * @param id id dokumentu.
     * @return dokument.
     */
    Document getById(int id);
}
