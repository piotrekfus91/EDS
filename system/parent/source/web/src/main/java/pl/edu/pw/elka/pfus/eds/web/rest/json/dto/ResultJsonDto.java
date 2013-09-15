package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import com.google.gson.annotations.SerializedName;
import pl.edu.pw.elka.pfus.eds.logic.exception.ConstraintsViolatedException;

import java.util.List;

/**
 * DTO zwracające informację o błędzie.
 */
public class ResultJsonDto {
    private ResultType result;

    @SerializedName("error_message")
    private String errorMessage;

    private Object data;

    @SerializedName("validation_errors")
    private List<String> validationErrors;

    public ResultJsonDto() {

    }

    public static ResultJsonDto from(ResultType result, String errorMessage, Object data) {
        ResultJsonDto resultJsonDto = new ResultJsonDto();
        resultJsonDto.setResult(result);
        resultJsonDto.setErrorMessage(errorMessage);
        if(data instanceof ConstraintsViolatedException) {
            ConstraintsViolatedException constraintsViolatedException = (ConstraintsViolatedException) data;
            resultJsonDto.setValidationErrors(constraintsViolatedException.getErrors());
        } else if(!(data instanceof Exception)) {
            resultJsonDto.setData(data);
        }
        return resultJsonDto;
    }

    public ResultType getResult() {
        return result;
    }

    public void setResult(ResultType result) {
        this.result = result;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<String> validationErrors) {
        this.validationErrors = validationErrors;
    }
}
