package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentSearchListExporter;

import java.util.List;

public class JsonDocumentSearchListExporterImpl extends AbstractSuccessFailureJsonExporter
        implements JsonDocumentSearchListExporter {
    @Override
    public String exportSuccess(List<DocumentSearchDto> object) {
        return success(object);
    }

    @Override
    public String exportFailure(String errorMessage, List<DocumentSearchDto> object) {
        return failure(errorMessage, object);
    }
}
