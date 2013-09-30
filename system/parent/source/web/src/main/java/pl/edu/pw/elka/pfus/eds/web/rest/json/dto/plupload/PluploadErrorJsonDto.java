package pl.edu.pw.elka.pfus.eds.web.rest.json.dto.plupload;

public class PluploadErrorJsonDto {
    private static final int ERROR_CODE = 101;

    private int code;
    private String message;

    public PluploadErrorJsonDto() {

    }

    public PluploadErrorJsonDto(String message) {
        this(ERROR_CODE, message);
    }

    public PluploadErrorJsonDto(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
