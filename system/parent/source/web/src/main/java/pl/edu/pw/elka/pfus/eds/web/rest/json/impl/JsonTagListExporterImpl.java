package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonTagListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.TagJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonTagListExporterImpl extends AbstractJsonExporter implements JsonTagListExporter {
    @Override
    public String export(List<Tag> tags) {
        List<TagJsonDto> dtos = new LinkedList<>();
        for(Tag tag : tags) {
            TagJsonDto dto = TagJsonDto.from(tag);
            dtos.add(dto);
        }
        return getGson().toJson(dtos);
    }
}
