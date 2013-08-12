package pl.edu.pw.elka.pfus.eds.util.config.impl;

import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigException;

import java.util.Date;

public class DummyConfig implements Config {
    @Override
    public String getString(String key) throws ConfigException {
        return "";
    }

    @Override
    public int getInt(String key) throws ConfigException {
        return 0;
    }

    @Override
    public Date getDate(String key) throws ConfigException {
        return new Date();
    }
}
