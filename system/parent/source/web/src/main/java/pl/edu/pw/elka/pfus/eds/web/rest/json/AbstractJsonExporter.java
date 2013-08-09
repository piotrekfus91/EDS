package pl.edu.pw.elka.pfus.eds.web.rest.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResultJsonDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResultType;

/**
 * Abstrakcyjna klasa dostarczająca wspólnego API dla exporterów JSON.
 */
public abstract class AbstractJsonExporter {
    public String success(Object data) {
        ResultJsonDto resultJsonDto = ResultJsonDto.from(ResultType.SUCCESS, "", data);
        return getGson().toJson(resultJsonDto);
    }

    public String failure(String errorMessage, Object data) {
        ResultJsonDto resultJsonDto = ResultJsonDto.from(ResultType.FAILURE, errorMessage, data);
        return getGson().toJson(resultJsonDto);
    }

    protected Gson getGson() {
        GsonBuilder gson = new GsonBuilder().setPrettyPrinting();
        return gson.create();
    }
}
