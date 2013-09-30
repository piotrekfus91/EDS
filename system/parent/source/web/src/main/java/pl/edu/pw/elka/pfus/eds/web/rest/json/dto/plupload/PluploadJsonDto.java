package pl.edu.pw.elka.pfus.eds.web.rest.json.dto.plupload;

import com.google.gson.annotations.SerializedName;

public class PluploadJsonDto {
    @SerializedName("jsonrpc")
    private String jsonRpc;

    private String result;

    private PluploadErrorJsonDto error;

    private String id;

    public PluploadJsonDto() {
        jsonRpc = "2.0";
        result = null;
        error = new PluploadErrorJsonDto();
        id = "id";
    }

    public PluploadJsonDto(String error) {
        this();
        this.error = new PluploadErrorJsonDto(error);
    }
}
