package pl.edu.pw.elka.pfus.eds.web.rest.json.dto.plupload;

public class PluploadErrorJsonDto {
    private int code;
    private String message;

    public PluploadErrorJsonDto() {

    }

    public PluploadErrorJsonDto(String message) {
        this(100, message);
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
