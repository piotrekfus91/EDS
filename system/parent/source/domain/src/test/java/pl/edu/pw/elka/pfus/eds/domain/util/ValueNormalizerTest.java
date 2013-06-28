package pl.edu.pw.elka.pfus.eds.domain.util;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class ValueNormalizerTest {
    @Test
    public void emptyValue() {
        assertThat(ValueNormalizer.normalizeValue("")).isEmpty();
    }

    @Test
    public void simpleValue() {
        assertThat(ValueNormalizer.normalizeValue("qwertyuiopasdfghjklzxcvbnm")).isEqualTo("qwertyuiopasdfghjklzxcvbnm");
    }

    @Test
    public void spaces() {
        assertThat(ValueNormalizer.normalizeValue("simple value")).isEqualTo("simplevalue");
    }

    @Test
    public void notAlphaNumericChars() {
        assertThat(ValueNormalizer.normalizeValue("a!b@c*de;f g0")).isEqualTo("abcdefg0");
    }

    @Test
    public void camelCase() {
        assertThat(ValueNormalizer.normalizeValue("aBcDeF123")).isEqualTo("abcdef123");
    }

    @Test
    public void polishChars() {
        assertThat(ValueNormalizer.normalizeValue("zażółć gęślą jaźń")).isEqualTo("zazolcgeslajazn");
        assertThat(ValueNormalizer.normalizeValue("ZAŻÓŁĆ GĘŚLĄ JAŹŃ")).isEqualTo("zazolcgeslajazn");
    }
}
