package pl.edu.pw.elka.pfus.eds.util.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractPropertiesConfig implements Config {
    @Override
    public int getInt(String key) throws ConfigException {
        String value = getString(key);
        try {
            return Integer.parseInt(value);
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }

    @Override
    public Date getDate(String key) throws ConfigException {
        String value = getString(key);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Config.DATE_FORMAT);
        try {
            return dateFormat.parse(value);
        } catch (Exception e) {
            throw new ConfigException(e);
        }
    }
}
