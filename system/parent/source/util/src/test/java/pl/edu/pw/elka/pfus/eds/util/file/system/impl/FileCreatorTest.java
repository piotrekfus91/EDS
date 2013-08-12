package pl.edu.pw.elka.pfus.eds.util.file.system.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.config.impl.MapConfig;
import pl.edu.pw.elka.pfus.eds.util.hash.impl.Md5InputStreamHasher;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class FileCreatorTest {
    private FileCreatorImpl fileCreator;

    @BeforeMethod
    private void setUp() {
        Map<String, String> map = new HashMap<>();
        map.put("part_length", "2");
        fileCreator = new FileCreatorImpl(new MapConfig(map), new Md5InputStreamHasher(), null);
    }

    @Test
    public void testFileSystemRootForSimple() throws Exception {
        assertThat(fileCreator.countFileSystemRoot("/home/piotrek/.eds")).isEqualTo("/home/piotrek/.eds");
    }

    @Test
    public void testFileSystemRootForTildaInTheMiddle() throws Exception {
        assertThat(fileCreator.countFileSystemRoot("/home/piotrek/~/.eds")).isEqualTo("/home/piotrek/~/.eds");
    }

    @Test
    public void testFileSystemRootStartingWithTilda() throws Exception {
        assertThat(fileCreator.countFileSystemRoot("~/.eds")).isEqualTo(System.getProperty("user.home") + "/.eds");
    }

    @Test
    public void testWindowsPathWithTilda() throws Exception {
        assertThat(fileCreator.countFileSystemRoot("C:\\.eds")).isEqualTo("C:\\.eds");
    }

    @Test
    public void testGetPartNumber() throws Exception {
        assertThat(fileCreator.getPartNumber("1", 4)).isEqualTo(1);
        assertThat(fileCreator.getPartNumber("12", 4)).isEqualTo(1);
        assertThat(fileCreator.getPartNumber("123", 4)).isEqualTo(1);
        assertThat(fileCreator.getPartNumber("1234", 4)).isEqualTo(1);
        assertThat(fileCreator.getPartNumber("12345", 4)).isEqualTo(2);
        assertThat(fileCreator.getPartNumber("123456", 4)).isEqualTo(2);
        assertThat(fileCreator.getPartNumber("1234567", 4)).isEqualTo(2);
        assertThat(fileCreator.getPartNumber("12345678", 4)).isEqualTo(2);
        assertThat(fileCreator.getPartNumber("123456789", 4)).isEqualTo(3);
    }

    @Test
    public void testSimpleSplit() throws Exception {
        assertThat(fileCreator.splitHash("1234567890")).isEqualTo(new String[]{"12", "34", "56", "78", "90"});
    }

    @Test(expectedExceptions = StringIndexOutOfBoundsException.class)
    public void testSplitWithNonZeroReminder() throws Exception {
        fileCreator.splitHash("123");
    }

    @Test
    public void testGetHashedPath() throws Exception {
        assertThat(fileCreator.getHashedPath("123456")).isEqualTo("12/34/56");
    }
}
