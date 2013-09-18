package pl.edu.pw.elka.pfus.eds.logic.search.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;

import java.io.InputStream;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class TikaExtractorTest {
    private TikaExtractor extractor;
    private FileManager fileManager;

    @BeforeMethod
    public void setUp() throws Exception {
        fileManager = mock(FileManager.class);
        extractor = new TikaExtractor(fileManager);
    }

    @Test
    public void testExtraction() throws Exception {
        InputStream inputStream = TikaExtractorTest.class.getClassLoader().getResourceAsStream("Praca.pdf");
        String extracted = extractor.extract(inputStream);

        assertThat(extracted.trim()).startsWith("Spis treści");
    }
}
