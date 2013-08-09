package pl.edu.pw.elka.pfus.eds.util.config.impl;

import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigException;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigTest;

public class ClassLoaderPropertiesConfigTest extends ConfigTest {
    @Override
    protected Config getConfig() {
        return new ClassLoaderPropertiesConfig("config.properties");
    }

    @Test(expectedExceptions = ConfigException.class)
    public void testMissingFile() throws Exception {
        new ClassLoaderPropertiesConfig("this file does not exist");
    }
}
