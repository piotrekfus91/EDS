package pl.edu.pw.elka.pfus.eds.util.config.impl;

import pl.edu.pw.elka.pfus.eds.util.config.AbstractPropertiesConfig;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigException;

import java.util.Map;

/**
 * Głównie na potrzeby testów.
 */
public class MapConfig extends AbstractPropertiesConfig {
    private Map<String, String> map;

    public MapConfig(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String getString(String key) throws ConfigException {
        String value = map.get(key);
        if(value == null)
            throw new ConfigException();
        return value;
    }
}
