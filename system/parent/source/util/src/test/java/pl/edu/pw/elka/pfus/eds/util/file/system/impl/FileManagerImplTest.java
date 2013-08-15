package pl.edu.pw.elka.pfus.eds.util.file.system.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.config.impl.MapConfig;
import pl.edu.pw.elka.pfus.eds.util.hash.impl.Md5ByteArrayHasher;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.Assertions.assertThat;

public class FileManagerImplTest {
    private FileManagerImpl fileCreator;

    @BeforeMethod
    private void setUp() {
        Map<String, String> map = new HashMap<>();
        map.put("part_length", "2");
        fileCreator = new FileManagerImpl(new MapConfig(map), new Md5ByteArrayHasher(), null);
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
        assertThat(fileCreator.getHashedPath("123456")).isEqualTo("12" + File.separator + "34" + File.separator + "56");
    }

    @Test(expectedExceptions = StringIndexOutOfBoundsException.class, dataProvider = "wrongPartsNumber")
    public void testValidatePartNumberEx(String hash, int partNumber) throws Exception {
        fileCreator.validatePartNumber(hash, partNumber);
    }

    @DataProvider
    private Object[][] wrongPartsNumber() {
        return new Object[][] {
                {"1", 2},
                {"123", 2},
                {"12345", 2},
                {"1", 4},
                {"123", 4},
                {"12345", 4}
        };
    }

    @Test(dataProvider = "correctPartsNumber")
    public void testValidatePartNumberNoEx(String hash, int partNumber) throws Exception {
        fileCreator.validatePartNumber(hash, partNumber);

        assertThat(true).isTrue();
    }

    @DataProvider
    private Object[][] correctPartsNumber() {
        return new Object[][] {
                {"12", 2},
                {"1234", 2},
                {"1256", 2},
                {"1234", 4},
                {"12345678", 4}
        };
    }
}
