package pl.edu.pw.elka.pfus.eds.util.config;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.fest.assertions.Assertions.assertThat;

public abstract class ConfigTest {
    private Config config;

    @BeforeMethod
    protected void setUp() {
        config = getConfig();
    }

    protected abstract Config getConfig();

    @Test(expectedExceptions = ConfigException.class)
    public void testGetMissingKey() throws Exception {
        config.getString("this key does not exist");
    }

    @Test
    public void testGetString() throws Exception {
        assertThat(config.getString("str")).isEqualTo("val");
    }

    @Test
    public void testGetInt() throws Exception {
        assertThat(config.getInt("int")).isEqualTo(5);
    }

    @Test(expectedExceptions = ConfigException.class)
    public void testGetIntWrongType() throws Exception {
        config.getInt("str");
    }

    @Test
    public void testGetDate() throws Exception {
        Calendar calendar = new GregorianCalendar(2013, 8, 22);
        assertThat(config.getDate("date")).isEqualTo(calendar.getTime());
    }

    @Test(expectedExceptions = ConfigException.class)
    public void testGetDateWrongType() throws Exception {
        config.getDate("str");
    }

    @Test
    public void testGetMultilineString() throws Exception {
        assertThat(config.getString("multiline")).isEqualTo("When you walk through a storm");
    }

    @Test
    public void testPolishChars() throws Exception {
        assertThat(config.getString("pl")).isEqualTo("zażółć gęślą jaźń");
    }
}
