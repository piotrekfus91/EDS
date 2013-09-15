package pl.edu.pw.elka.pfus.eds.util.file.system.impl;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.config.impl.MapConfig;
import pl.edu.pw.elka.pfus.eds.util.file.system.exception.FileSystemException;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class PathCreatorImplTest {
    private PathCreatorImpl pathCreator;
    private static final String ROOT_DIR = System.getProperty("java.io.tmpdir") + File.separator + "eds";
    public static final String SAMPLE_PATH = ROOT_DIR + File.separator + "sample";

    @BeforeMethod
    private void setUp() {
        Map<String, String> configMap = new HashMap<>();
        configMap.put("file_system_root", ROOT_DIR);
        pathCreator = new PathCreatorImpl(new MapConfig(configMap));
        File file = new File(ROOT_DIR);
        file.mkdir();
        assertThat(file.exists()).isTrue();
    }

    @AfterMethod
    private void tearDown() throws Exception {
        File file = new File(ROOT_DIR);
        FileUtils.deleteDirectory(file);
        assertThat(file.exists()).isFalse();
    }

    @Test
    public void testCreation() throws Exception {
        File dir = new File(SAMPLE_PATH);
        assertThat(dir.exists()).isFalse();

        pathCreator.createIfNotExists(SAMPLE_PATH);

        assertThat(dir.exists()).isTrue();
    }

    @Test
    public void testCreationOfExisting() throws Exception {
        File dir = new File(ROOT_DIR);
        assertThat(dir.exists()).isTrue();

        pathCreator.createIfNotExists(ROOT_DIR);

        assertThat(dir.exists()).isTrue();
    }

    @Test(expectedExceptions = FileSystemException.class)
    public void testCreateDirOnExistingDir() throws Exception {
        File file = new File(SAMPLE_PATH);
        file.createNewFile();

        pathCreator.createIfNotExists(SAMPLE_PATH, "jlaksdf");
    }

    @Test
    public void testCreatePath() throws Exception {
        String[] path = new String[] {"a", "b", "c"};

        pathCreator.createPath(ROOT_DIR, path);

        assertThat(new File(ROOT_DIR + File.separator + "a").exists())
                .isTrue();
        assertThat(new File(ROOT_DIR + File.separator + "a" + File.separator + "b").exists())
                .isTrue();
        assertThat(new File(ROOT_DIR + File.separator + "a" + File.separator + "b" + File.separator + "c").exists())
                .isTrue();
    }

    @Test
    public void testCreateRoot() throws Exception {
        FileUtils.deleteDirectory(new File(ROOT_DIR));

        pathCreator.createNecessaryDirectories();

        assertThat(new File(ROOT_DIR).exists()).isTrue();
    }
}
