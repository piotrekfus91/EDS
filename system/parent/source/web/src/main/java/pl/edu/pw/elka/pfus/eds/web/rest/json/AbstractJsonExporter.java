package pl.edu.pw.elka.pfus.eds.web.rest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Abstrakcyjna klasa dostarczająca wspólnego API dla exporterów JSON.
 */
public abstract class AbstractJsonExporter<T> implements JsonExporter<T> {
    protected Gson getGson() {
        GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
        return gson.create();
    }
}
