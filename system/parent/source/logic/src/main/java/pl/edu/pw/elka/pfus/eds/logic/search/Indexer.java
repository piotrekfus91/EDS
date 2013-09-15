package pl.edu.pw.elka.pfus.eds.logic.search;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import java.io.IOException;

public interface Indexer {
    void index(Document document) throws IOException;
}
