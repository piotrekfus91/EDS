package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.dao.dto.SharedResourceGroupDto;

public class SharedResourceGroupJsonDto {
    private String name;
    private boolean shared;

    public SharedResourceGroupJsonDto() {

    }

    public SharedResourceGroupJsonDto(String name, boolean shared) {
        this.name = name;
        this.shared = shared;
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

    public boolean isShared() {
        return shared;
    }

    public void setShared(boolean shared) {
        this.shared = shared;
    }
}
