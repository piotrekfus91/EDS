package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonFileSystemEntryListExporter;

import java.util.LinkedList;
import java.util.List;

public class JsonFileSystemEntryListExporterImpl extends AbstractJsonExporter<List<FileSystemEntry>>
        implements JsonFileSystemEntryListExporter {
    private static final Logger logger = Logger.getLogger(JsonFileSystemEntryListExporterImpl.class);

    @Override
    public String export(List<FileSystemEntry> entries) {
        List<FileSystemEntryJsonDto> exports = new LinkedList<>();
        for(FileSystemEntry entry : entries) {
            exports.add(FileSystemEntryJsonDto.from(entry));
        }
        String result = getGson().toJson(exports);
        logger.info("exported: " + result);
        return result;
    }
}
