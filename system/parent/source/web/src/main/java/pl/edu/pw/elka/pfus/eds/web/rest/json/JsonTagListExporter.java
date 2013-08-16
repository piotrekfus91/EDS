package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

import java.util.List;

/**
 * Eksporter dla listy tagów.
 */
public interface JsonTagListExporter {
    String export(List<Tag> tags);
}
