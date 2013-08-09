package pl.edu.pw.elka.pfus.eds.util.config.impl;

import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigTest;

public class DefaultClassLoaderPropertiesConfigTest extends ConfigTest {
    @Override
    protected Config getConfig() {
        return new DefaultClassLoaderPropertiesConfig();
    }
}
