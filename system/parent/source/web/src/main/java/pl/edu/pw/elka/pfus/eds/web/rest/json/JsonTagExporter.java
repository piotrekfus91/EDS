package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;

public interface JsonTagExporter {
    String export(Tag tag);
}
