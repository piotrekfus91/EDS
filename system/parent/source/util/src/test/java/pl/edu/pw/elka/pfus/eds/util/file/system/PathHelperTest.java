package pl.edu.pw.elka.pfus.eds.util.file.system;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class PathHelperTest {
    @Test
    public void testFileSystemRootForSimple() throws Exception {
        assertThat(PathHelper.countFileSystemRoot("/home/piotrek/.eds")).isEqualTo("/home/piotrek/.eds");
    }

    @Test
    public void testFileSystemRootForTildaInTheMiddle() throws Exception {
        assertThat(PathHelper.countFileSystemRoot("/home/piotrek/~/.eds")).isEqualTo("/home/piotrek/~/.eds");
    }

    @Test
    public void testFileSystemRootStartingWithTilda() throws Exception {
        assertThat(PathHelper.countFileSystemRoot("~/.eds")).isEqualTo(System.getProperty("user.home") + "/.eds");
    }

    @Test
    public void testWindowsPathWithTilda() throws Exception {
        assertThat(PathHelper.countFileSystemRoot("C:\\.eds")).isEqualTo("C:\\.eds");
    }
}
