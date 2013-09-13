package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentsNumberInDaysListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DocumentsNumberInDaysJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonDocumentsNumberInDaysListExporterImpl extends AbstractJsonExporter
        implements JsonDocumentsNumberInDaysListExporter {
    @Override
    public String export(List<DocumentsNumberInDaysDto> list) {
        List<DocumentsNumberInDaysJsonDto> dtos = new LinkedList<>();
        for(DocumentsNumberInDaysDto dto : list) {
            dtos.add(DocumentsNumberInDaysJsonDto.from(dto));
        }
        return getGson().toJson(dtos);
    }
}
