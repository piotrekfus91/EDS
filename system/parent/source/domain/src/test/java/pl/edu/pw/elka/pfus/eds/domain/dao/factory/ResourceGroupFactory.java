package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;

public class ResourceGroupFactory implements EntityFactory<ResourceGroup> {
    private static int counter = 0;
    private UserFactory userFactory = new UserFactory();

    @Override
    public ResourceGroup getSampleEntity() {
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.setName("resource group" + counter++);
        resourceGroup.setFounder(userFactory.getSampleEntity());
        return resourceGroup;
    }
}
