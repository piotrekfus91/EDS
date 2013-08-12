package pl.edu.pw.elka.pfus.eds.util.hash.impl;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class AbstractHasherTest {
    // konkretny hasher nie jest istotny
    // sprawdzamy metody bazowe abstrakcyjnej klasy
    private Md5InputStreamHasher hasher;

    @BeforeMethod
    private void setUp() {
        hasher = new Md5InputStreamHasher();
    }

    @Test
    public void testPaddingMissingZeros() throws Exception {
        assertThat(hasher.addStartingPaddingOfZeros("123456789012345678901234567890"))
                .isEqualTo("00123456789012345678901234567890");
    }

    @Test
    public void testPaddingNoMissingZeros() throws Exception {
        assertThat(hasher.addStartingPaddingOfZeros("12123456789012345678901234567890"))
                .isEqualTo("12123456789012345678901234567890");
    }
}
