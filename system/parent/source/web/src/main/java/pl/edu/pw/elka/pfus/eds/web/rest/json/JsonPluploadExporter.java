package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.plupload.PluploadJsonDto;

/**
 * Eksporter dla formatu plupload.
 */
public interface JsonPluploadExporter {
    String export(PluploadJsonDto dto);
}
