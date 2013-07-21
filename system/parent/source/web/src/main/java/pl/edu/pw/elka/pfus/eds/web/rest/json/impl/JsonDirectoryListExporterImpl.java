package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import org.apache.log4j.Logger;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.web.rest.json.AbstractJsonExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonDirectoryListExporter;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.DirectoryJsonDto;

import java.util.LinkedList;
import java.util.List;

public class JsonDirectoryListExporterImpl extends AbstractJsonExporter<List<Directory>> implements JsonDirectoryListExporter {
    private static final Logger logger = Logger.getLogger(JsonDirectoryListExporterImpl.class);

    @Override
    public String export(List<Directory> directories) {
        List<DirectoryJsonDto> exportedDirectories = new LinkedList<>();
        for(Directory directory : directories) {
            exportedDirectories.add(DirectoryJsonDto.from(directory));
        }
        String result = getGson().toJson(exportedDirectories);
        logger.info("exported: " + result);
        return result;
    }
}
