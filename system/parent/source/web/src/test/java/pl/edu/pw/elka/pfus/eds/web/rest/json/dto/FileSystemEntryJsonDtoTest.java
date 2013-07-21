package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;

import static org.fest.assertions.Assertions.assertThat;

public class FileSystemEntryJsonDtoTest {
    private FileSystemEntryJsonDto dto = new FileSystemEntryJsonDto();

    @BeforeMethod
    private void beforeMethod() {
        dto.setFolder("");
    }

    @Test
    public void testIsFolderForDirectory() {
        dto.setFolder(Directory.class);
        assertThat(dto.getFolder()).isTrue();
    }

    @Test
    public void testIsFolderForDocument() {
        dto.setFolder(Document.class);
        assertThat(dto.getFolder()).isFalse();
    }
}
