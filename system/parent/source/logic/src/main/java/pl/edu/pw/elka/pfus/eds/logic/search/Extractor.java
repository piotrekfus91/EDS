package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import java.io.InputStream;

/**
 * API do wyciągania zawartości tekstowej dokumentu.
 */
public interface Extractor {
    /**
     * Wyciąga zawartość tekstową z dokumentu.
     *
     * @param document dokument, którego treść chcemy wyciągnąć.
     * @return zawartość dokumentu.
     */
    String extract(Document document);

    /**
     * Wyciąga zawartość tekstową z dokumentu.
     *
     * @param inputStream strumień, którego treść chcemy wyciągnąć.
     * @return zawartość dokumentu.
     */
    String extract(InputStream inputStream);
}
