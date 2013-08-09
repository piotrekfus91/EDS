package pl.edu.pw.elka.pfus.eds.util.config.impl;

import pl.edu.pw.elka.pfus.eds.util.config.AbstractPropertiesConfig;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigException;

import java.util.Properties;

public class ClassLoaderPropertiesConfig extends AbstractPropertiesConfig {
    private Properties props;

    public ClassLoaderPropertiesConfig(String filePath) {
        props = new Properties();
        try {
            props.load(ClassLoaderPropertiesConfig.class.getClassLoader().getResourceAsStream(filePath));
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    @Override
    public String getString(String key) throws ConfigException {
        String value = props.getProperty(key);
        if(value == null)
            throw new ConfigException("key " + key + " not found");
        return value;
    }
}
