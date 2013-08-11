package pl.edu.pw.elka.pfus.eds.util.config.impl;

import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.ConfigTest;

import java.util.HashMap;
import java.util.Map;

public class MapConfigTest extends ConfigTest {
    @Override
    protected Config getConfig() {
        Map<String, String> map = new HashMap<>();
        map.put("str", "val");
        map.put("int", "5");
        map.put("date", "22-09-2013");
        map.put("pl", "zażółć gęślą jaźń");
        map.put("multiline", "When you walk through a storm");
        return new MapConfig(map);
    }
}
