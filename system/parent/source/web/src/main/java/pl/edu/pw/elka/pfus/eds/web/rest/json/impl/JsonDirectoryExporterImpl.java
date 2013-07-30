package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DirectoryJsonDto;

public class JsonDirectoryExporterImpl extends AbstractJsonExporter<Directory> implements JsonDirectoryExporter {
    @Override
    public String export(Directory directory) {
        DirectoryJsonDto dto = DirectoryJsonDto.from(directory);
        return getGson().toJson(dto);
    }
}
