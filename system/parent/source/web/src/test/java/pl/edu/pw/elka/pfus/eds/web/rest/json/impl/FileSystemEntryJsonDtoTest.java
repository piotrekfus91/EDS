package pl.edu.pw.elka.pfus.eds.web.rest.json.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FileSystemEntryJsonDtoTest {
    private FileSystemEntryJsonDto dto = new FileSystemEntryJsonDto();

    @BeforeMethod
    private void beforeMethod() {
        dto.setType("");
    }

    @Test
    public void testOneWordLowerCaseType() {
        dto.setType("abc");
        assertThat(dto.getType()).isEqualTo("abc");
    }

    @Test
    public void testOneWordStartingUpperCaseType() {
        dto.setType("Abc");
        assertThat(dto.getType()).isEqualTo("abc");
    }

    @Test
    public void testTwoWordsCamelCase() {
        dto.setType("AbcDef");
        assertThat(dto.getType()).isEqualTo("abc_def");
    }

    @Test
    public void testTwoWordsUnderscore() {
        dto.setType("abc_def");
        assertThat(dto.getType()).isEqualTo("abc_def");
    }
}
