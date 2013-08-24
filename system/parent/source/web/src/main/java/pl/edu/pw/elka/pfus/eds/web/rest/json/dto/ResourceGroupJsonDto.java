package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;

public class ResourceGroupJsonDto {
    private String name;
    private String founder;

    public ResourceGroupJsonDto() {

    }

    public ResourceGroupJsonDto(String name, String founder) {
        this.name = name;
        this.founder = founder;
    }

    public static ResourceGroupJsonDto from(ResourceGroup resourceGroup) {
        if(resourceGroup == null)
            return new ResourceGroupJsonDto();
        return new ResourceGroupJsonDto(resourceGroup.getName(), resourceGroup.getFounder().getFriendlyName());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFounder() {
        return founder;
    }

    public void setFounder(String founder) {
        this.founder = founder;
    }
}
