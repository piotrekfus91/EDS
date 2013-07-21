package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import static org.fest.assertions.Assertions.assertThat;

public class FileSystemEntryJsonDtoTest {
    private FileSystemEntryJsonDto dto = new FileSystemEntryJsonDto();

    @BeforeMethod
    private void beforeMethod() {
        dto.setIsFolder("");
    }

    @Test
    public void testIsFolderForDirectory() {
        dto.setIsFolder(Directory.class);
        assertThat(dto.getIsFolder()).isTrue();
    }

    @Test
    public void testIsFolderForDocument() {
        dto.setIsFolder(Document.class);
        assertThat(dto.getIsFolder()).isFalse();
    }
}
