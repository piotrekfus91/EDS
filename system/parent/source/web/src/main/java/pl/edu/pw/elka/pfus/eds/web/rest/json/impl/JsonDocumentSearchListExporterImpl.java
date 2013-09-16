package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.logic.search.dto.DocumentSearchDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentSearchListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DownloadableDocumentSearchDto;

import java.util.LinkedList;
import java.util.List;

public class JsonDocumentSearchListExporterImpl extends AbstractSuccessFailureJsonExporter
        implements JsonDocumentSearchListExporter {
    @Override
    public String exportSuccess(List<DocumentSearchDto> list) {
        List<DownloadableDocumentSearchDto> dtos = new LinkedList<>();
        for(DocumentSearchDto documentSearchDto : list) {
            dtos.add(DownloadableDocumentSearchDto.from(documentSearchDto));
        }
        return success(dtos);
    }

    @Override
    public String exportFailure(String errorMessage, List<DocumentSearchDto> list) {
        List<DownloadableDocumentSearchDto> dtos = new LinkedList<>();
        for(DocumentSearchDto documentSearchDto : list) {
            dtos.add(DownloadableDocumentSearchDto.from(documentSearchDto));
        }
        return failure(errorMessage, dtos);
    }
}
