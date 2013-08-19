package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDocumentExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.TaggedCommentedDocumentJsonDto;

public class JsonDocumentExporterImpl extends AbstractSuccessFailureJsonExporter implements JsonDocumentExporter {
    @Override
    public String exportSuccess(Document object) {
        TaggedCommentedDocumentJsonDto dto = TaggedCommentedDocumentJsonDto.from(object);
        return success(dto);
    }

    @Override
    public String exportFailure(String errorMessage, Document object) {
        TaggedCommentedDocumentJsonDto dto = TaggedCommentedDocumentJsonDto.from(object);
        return failure(errorMessage, dto);
    }
}
