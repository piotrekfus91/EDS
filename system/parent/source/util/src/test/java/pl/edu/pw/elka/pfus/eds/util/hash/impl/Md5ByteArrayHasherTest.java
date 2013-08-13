package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class Md5ByteArrayHasherTest {
    private Md5ByteArrayHasher hasher;

    @BeforeMethod
    private void setUp() {
        hasher = new Md5ByteArrayHasher();
    }

    @Test(dataProvider = "byteArraysAndHashes")
    public void testHash(byte[] input, String expected) throws Exception {
        assertThat(hasher.getString(input)).isEqualTo(expected);
    }

    @DataProvider
    private Object[][] byteArraysAndHashes() {
        return new Object[][] {
                {"test".getBytes(), "098f6bcd4621d373cade4e832627b4f6"}
        };
    }
}
