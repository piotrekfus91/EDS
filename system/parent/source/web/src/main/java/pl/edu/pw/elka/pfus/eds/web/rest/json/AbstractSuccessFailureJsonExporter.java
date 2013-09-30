package pl.edu.pw.elka.pfus.eds.web.rest.json;

import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResultJsonDto;
import pl.edu.pw.elka.pfus.eds.web.rest.json.dto.ResultType;

/**
 * Abstrakcyjna klasa dostarczająca wspólnego API dla exporterów JSON.
 */
public abstract class AbstractSuccessFailureJsonExporter extends AbstractJsonExporter {
    protected String success(Object data) {
        ResultJsonDto resultJsonDto = ResultJsonDto.from(ResultType.SUCCESS, "", data);
        return getGson().toJson(resultJsonDto);
    }

    protected String failure(String errorMessage, Object data) {
        ResultJsonDto resultJsonDto = ResultJsonDto.from(ResultType.FAILURE, errorMessage, data);
        return getGson().toJson(resultJsonDto);
    }

    public String exportFailure(LogicException e) {
        ResultJsonDto resultJsonDto = ResultJsonDto.from(ResultType.FAILURE, e.getMessage(), e);
        return getGson().toJson(resultJsonDto);
    }
}
