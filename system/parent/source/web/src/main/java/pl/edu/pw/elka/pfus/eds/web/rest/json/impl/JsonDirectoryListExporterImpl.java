package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DirectoryJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonDirectoryListExporterImpl extends AbstractJsonExporter implements JsonDirectoryListExporter {
    @Override
    public String exportSuccess(List<Directory> directories) {
        List<DirectoryJsonDto> exportedDirectories = new LinkedList<>();
        for(Directory directory : directories) {
            exportedDirectories.add(DirectoryJsonDto.from(directory));
        }
        return success(exportedDirectories);
    }

    @Override
    public String exportFailure(String errorMessage, List<Directory> object) {
        List<DirectoryJsonDto> exportedDirectories = null;
        if (object != null) {
            exportedDirectories = new LinkedList<>();
            for (Directory directory : object) {
                exportedDirectories.add(DirectoryJsonDto.from(directory));
            }
        }
        return failure(errorMessage, exportedDirectories);
    }
}
