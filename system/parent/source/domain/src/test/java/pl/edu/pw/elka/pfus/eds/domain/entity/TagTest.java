package pl.edu.pw.elka.pfus.eds.domain.entity;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TagTest {
    private Tag tag;

    @BeforeMethod
    private void beforeMethod() {
        tag = new Tag();
    }

    @Test
    public void emptyValue() {
        assertThat(tag.getValue()).isNullOrEmpty();
        assertThat(tag.getNormalizedValue()).isNullOrEmpty();
    }

    @Test
    public void notEmptyValue() {
        tag.setValue("qwertyuiopasdfghjklzxcvbnm");
        assertThat(tag.getValue()).isEqualTo("qwertyuiopasdfghjklzxcvbnm");
        assertThat(tag.getNormalizedValue()).isEqualTo("qwertyuiopasdfghjklzxcvbnm");
    }

    @Test
    public void compositeValue() {
        tag.setValue("zażółć_gęślą !jaŹŃ");
        assertThat(tag.getValue()).isEqualTo("zażółć_gęślą !jaŹŃ");
        assertThat(tag.getNormalizedValue()).isEqualTo("zazolcgeslajazn");
    }
}
