package pl.edu.pw.elka.pfus.eds.logic.document;

import pl.edu.pw.elka.pfus.eds.logic.document.dto.DocumentNameBytesDto;

/**
 * Interfejs udostępnia API do downloadu dokumentów.
 */
public interface DocumentDownloader {
    /**
     * Zwraca DTO dokumentu, zawierające jego nazwę i zawartość.
     *
     * @param documentId id dokumentu.
     * @return dto.
     */
    DocumentNameBytesDto getDocumentNameAndBytesById(int documentId);
}
