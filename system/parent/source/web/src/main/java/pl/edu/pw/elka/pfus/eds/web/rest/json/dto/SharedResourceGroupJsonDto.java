package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import com.google.gson.annotations.SerializedName;
import pl.edu.pw.elka.pfus.eds.domain.dao.dto.SharedResourceGroupDto;

public class SharedResourceGroupJsonDto {
    private String name;
    @SerializedName("isShared")
    private boolean isShared;

    public SharedResourceGroupJsonDto() {

    }

    public SharedResourceGroupJsonDto(String name, boolean shared) {
        this.name = name;
        isShared = shared;
    }

    public static SharedResourceGroupJsonDto from(SharedResourceGroupDto sharedResourceGroupDto) {
        if(sharedResourceGroupDto == null)
            return new SharedResourceGroupJsonDto();
        return new SharedResourceGroupJsonDto(sharedResourceGroupDto.getResourceGroup().getName(),
                sharedResourceGroupDto.isShared());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getShared() {
        return isShared;
    }

    public void setShared(boolean shared) {
        isShared = shared;
    }
}
