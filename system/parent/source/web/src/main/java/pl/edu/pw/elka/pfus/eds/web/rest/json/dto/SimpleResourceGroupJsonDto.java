package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;

public class SimpleResourceGroupJsonDto {
    private String name;
    private String description;

    public SimpleResourceGroupJsonDto() {

    }

    public SimpleResourceGroupJsonDto(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public static SimpleResourceGroupJsonDto from(ResourceGroup resourceGroup) {
        if(resourceGroup == null)
            return new SimpleResourceGroupJsonDto();
        return new SimpleResourceGroupJsonDto(resourceGroup.getName(), resourceGroup.getDescription());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
