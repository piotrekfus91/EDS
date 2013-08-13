package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractSuccessFailureJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonFileSystemEntryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.FileSystemEntryJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonFileSystemEntryListExporterImpl extends AbstractSuccessFailureJsonExporter implements JsonFileSystemEntryListExporter {
    @Override
    public String exportSuccess(List<FileSystemEntry> entries) {
        List<FileSystemEntryJsonDto> exports = new LinkedList<>();
        for(FileSystemEntry entry : entries) {
            exports.add(FileSystemEntryJsonDto.from(entry));
        }
        return super.success(exports);
    }

    @Override
    public String exportFailure(String errorMessage, List<FileSystemEntry> object) {
        List<FileSystemEntryJsonDto> exports = null;
        if (object != null) {
            exports = new LinkedList<>();
            for (FileSystemEntry entry : object) {
                exports.add(FileSystemEntryJsonDto.from(entry));
            }
        }
        return super.failure(errorMessage, exports);
    }
}
