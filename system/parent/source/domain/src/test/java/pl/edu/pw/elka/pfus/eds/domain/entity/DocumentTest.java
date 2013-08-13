package pl.edu.pw.elka.pfus.eds.domain.entity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.GregorianCalendar;

import static org.fest.assertions.Assertions.assertThat;

public class DocumentTest {
    private Document document;
    private User owner;
    private Directory directory;

    @BeforeMethod
    private void setUp() {
        document = new Document();
        document.setName("name");

        owner = new User();
        directory = new Directory();
    }

    @Test
    public void testGetStringPathIfDirectoryIsNull() throws Exception {
        assertThat(document.getStringPath()).isEqualTo("/name");
    }

    @Test
    public void testGetStringPathIfDirectoryIsNotNull() throws Exception {
        Directory directory = new Directory();
        directory.setName("dir");
        document.setDirectory(directory);

        assertThat(document.getStringPath()).isEqualTo("/dir/name");
    }

    @Test
    public void testSetDirectory() throws Exception {
        directory.setOwner(owner);
        document.setDirectory(directory);

        assertThat(document.getOwner()).isSameAs(owner);
    }

    @Test(dependsOnMethods = "testSetDirectory")
    public void testUnbindDocumentFromDirectory() throws Exception {
        directory.setOwner(owner);
        document.setDirectory(directory);

        document.setDirectory(null);

        assertThat(document.getOwner()).isNull();
    }

    @Test
    public void testGetFileSystemName() throws Exception {
        document.setCreated(new GregorianCalendar(1971, 0, 1).getTime());

        assertThat(document.getFileSystemName()).isEqualTo("31532400000");
    }
}
