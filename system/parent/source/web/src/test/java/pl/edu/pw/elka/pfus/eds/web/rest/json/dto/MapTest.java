package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    private static final Logger logger = Logger.getLogger(MapTest.class);

    private Map<String, Boolean> map = new HashMap<>();

    @Test
    public void testJsonify() throws Exception {
        map.put("test1", true);
        map.put("test2", false);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        logger.info(gson.toJson(map));
    }
}
