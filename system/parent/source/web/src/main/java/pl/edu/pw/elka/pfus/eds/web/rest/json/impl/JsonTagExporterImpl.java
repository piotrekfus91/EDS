package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.TagJsonDto;

public class JsonTagExporterImpl extends AbstractJsonExporter implements JsonTagExporter {
    @Override
    public String export(Tag tag) {
        TagJsonDto dto = TagJsonDto.from(tag);
        return getGson().toJson(dto);
    }
}
