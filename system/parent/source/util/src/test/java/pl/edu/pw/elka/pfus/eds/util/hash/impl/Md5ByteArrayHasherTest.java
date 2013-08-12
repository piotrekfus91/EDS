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

    @Test(dataProvider = "byteArrayAndHashes")
    public void testHash(byte[] input, String expected) throws Exception {
        assertThat(hasher.getString(input)).isEqualTo(expected);
    }

    @DataProvider
    private Object[][] byteArraysAndHashes() {
        return new Object[][] {
                {"test".getBytes(), "098f6bcd4621d373cade4e832627b4f6"},
                {"zażółć gęślą jaźń".getBytes(), "930b9b78f35b80ccb414cd7486bb4cdb"}
        };
    }
}
