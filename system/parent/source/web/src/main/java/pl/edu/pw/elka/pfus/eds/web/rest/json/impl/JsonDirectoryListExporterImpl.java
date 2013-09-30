package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DirectoryJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonDirectoryListExporterImpl extends AbstractSuccessFailureJsonExporter implements JsonDirectoryListExporter {
    @Override
    public String exportSuccess(List<Directory> directories) {
        List<DirectoryJsonDto> exportedDirectories = getDtos(directories);
        return success(exportedDirectories);
    }

    @Override
    public String exportFailure(String errorMessage, List<Directory> directories) {
        List<DirectoryJsonDto> exportedDirectories = getDtos(directories);
        return failure(errorMessage, exportedDirectories);
    }

    private List<DirectoryJsonDto> getDtos(List<Directory> directories) {
        List<DirectoryJsonDto> exportedDirectories = new LinkedList<>();
        if(directories != null) {
            for (Directory directory : directories) {
                exportedDirectories.add(DirectoryJsonDto.from(directory));
            }
        }
        return exportedDirectories;
    }
}
