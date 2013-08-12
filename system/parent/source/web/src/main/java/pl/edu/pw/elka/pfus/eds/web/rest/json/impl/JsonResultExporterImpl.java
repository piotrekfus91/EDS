package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonResultExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResultJsonDto;

public class JsonResultExporterImpl extends AbstractJsonExporter implements JsonResultExporter {

    @Override
    public String exportSuccess(ResultJsonDto object) {
        return super.success(object);
    }

    @Override
    public String exportFailure(String errorMessage, ResultJsonDto object) {
        return super.failure(errorMessage, object);
    }
}
