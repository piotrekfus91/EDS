package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;

import java.util.List;

/**
 * Eksportuje listę {@link FileSystemEntry} do stringa.
 */
public interface JsonFileSystemEntryListExporter extends JsonExporter<List<FileSystemEntry>> {
}
