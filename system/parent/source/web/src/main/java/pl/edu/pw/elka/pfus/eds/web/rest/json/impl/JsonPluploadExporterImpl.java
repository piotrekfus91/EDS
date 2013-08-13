package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonPluploadExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.plupload.PluploadJsonDto;

public class JsonPluploadExporterImpl extends AbstractJsonExporter implements JsonPluploadExporter {
    @Override
    public String export(PluploadJsonDto dto) {
        return getGson().toJson(dto);
    }
}
