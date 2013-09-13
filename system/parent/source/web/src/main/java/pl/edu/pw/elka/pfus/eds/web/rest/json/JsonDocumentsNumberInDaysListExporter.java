package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.DocumentsNumberInDaysDto;

import java.util.List;

public interface JsonDocumentsNumberInDaysListExporter {
    String export(List<DocumentsNumberInDaysDto> documentsNumberInDaysJsonDto);
}
