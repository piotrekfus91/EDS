package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.web.rest.json.JsonFileSystemEntryListExporter;

import java.util.LinkedList;
import java.util.List;

public class JsonFileSystemEntryListExporterImplTest {
    @Test
    public void testSuccess() throws Exception {
        Directory directory = new Directory();
        directory.setId(123);
        directory.setName("direk");
        Document document = new Document();
        document.setId(45);
        document.setName("docek");
        List<FileSystemEntry> entries = new LinkedList<>();
        entries.add(directory);
        entries.add(document);

        JsonFileSystemEntryListExporter exporter = new JsonFileSystemEntryListExporterImpl();
        System.out.println(exporter.exportSuccess(entries));
    }

    @Test
    public void testFailureWithNotNull() throws Exception {
        Directory directory = new Directory();
        directory.setId(123);
        directory.setName("direk");

        List<FileSystemEntry> entries = new LinkedList<>();

        JsonFileSystemEntryListExporter exporter = new JsonFileSystemEntryListExporterImpl();
        System.out.println(exporter.exportFailure("this is ERROR", entries));
    }

    @Test
    public void testFailureWithNull() throws Exception {
        JsonFileSystemEntryListExporter exporter = new JsonFileSystemEntryListExporterImpl();
        System.out.println(exporter.exportFailure("this is ERROR", null));
    }
}
