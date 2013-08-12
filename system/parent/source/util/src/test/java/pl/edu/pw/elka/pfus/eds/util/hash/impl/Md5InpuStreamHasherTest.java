package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.InputStream;

import static org.fest.assertions.Assertions.assertThat;

public class Md5InpuStreamHasherTest {
    private Md5InputStreamHasher hasher;

    @BeforeMethod
    private void setUp() {
        hasher = new Md5InputStreamHasher();
    }

    @Test(dataProvider = "filesAndHashes")
    public void test(String filePath, String expectedHash) throws Exception {
        InputStream inputStream = Md5InpuStreamHasherTest.class.getClassLoader().getResourceAsStream(filePath);

        assertThat(hasher.getString(inputStream)).isEqualTo(expectedHash);
    }

    @DataProvider
    private Object[][] filesAndHashes() {
        return new Object[][] {
                {"hash/test.txt", "098f6bcd4621d373cade4e832627b4f6"},
                {"hash/test_pl.txt", "930b9b78f35b80ccb414cd7486bb4cdb"},
                {"hash/wallpaper.jpg", "76cbc24d83770ddee3c46c36084acda6"}
        };
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void testException() throws Exception {
        hasher = new MockMd5InpuStreamHasher();
        hasher.getString(null);
    }
}
