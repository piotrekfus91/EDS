package pl.edu.pw.elka.pfus.eds.util;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class StringHelperTest {
    @Test
    public void testDecoratingWithSlash() throws Exception {
        assertThat(StringHelper.decorateWithLeadingSlash("abc")).isEqualTo("abc/");
        assertThat(StringHelper.decorateWithLeadingSlash("abc/")).isEqualTo("abc/");
    }
}
