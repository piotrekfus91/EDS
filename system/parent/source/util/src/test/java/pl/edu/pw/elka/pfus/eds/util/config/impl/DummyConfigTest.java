package pl.edu.pw.elka.pfus.eds.util.config.impl;

import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.config.Config;

import static org.fest.assertions.Assertions.assertThat;

public class DummyConfigTest {
    private Config config = new DummyConfig();

    @Test
    public void testGetString() throws Exception {
        assertThat(config.getString("")).isEqualTo("");
    }

    @Test
    public void testGetInt() throws Exception {
        assertThat(config.getInt("")).isEqualTo(0);
    }

    @Test
    public void testGetDate() throws Exception {
        assertThat(config.getDate("")).isNotNull();
    }
}
