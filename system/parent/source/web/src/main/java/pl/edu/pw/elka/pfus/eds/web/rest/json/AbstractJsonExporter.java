package pl.edu.pw.elka.pfus.eds.web.rest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class AbstractJsonExporter {
    protected Gson getGson() {
        GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
        return gson.create();
    }
}
