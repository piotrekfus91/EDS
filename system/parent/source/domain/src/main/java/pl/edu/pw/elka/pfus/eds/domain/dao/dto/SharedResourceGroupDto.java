package pl.edu.pw.elka.pfus.eds.domain.dao.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;

public class SharedResourceGroupDto {
    private ResourceGroup resourceGroup;
    private boolean shared;

    public SharedResourceGroupDto(ResourceGroup resourceGroup, boolean shared) {
        this.resourceGroup = resourceGroup;
        this.shared = shared;
    }

    public ResourceGroup getResourceGroup() {
        return resourceGroup;
    }

    public boolean isShared() {
        return shared;
    }
}
