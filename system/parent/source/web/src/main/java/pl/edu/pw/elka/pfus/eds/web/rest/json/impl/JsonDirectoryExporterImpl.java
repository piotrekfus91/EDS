package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DirectoryJsonDto;

public class JsonDirectoryExporterImpl extends AbstractJsonExporter implements JsonDirectoryExporter {
    @Override
    public String exportSuccess(Directory directory) {
        DirectoryJsonDto dto = DirectoryJsonDto.from(directory);
        return super.success(dto);
    }

    @Override
    public String exportFailure(String errorMessage, Directory data) {
        DirectoryJsonDto dto = DirectoryJsonDto.from(data);
        return super.failure(errorMessage, dto);
    }
}
