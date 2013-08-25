package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupFinder;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupModifier;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupService;

import java.util.List;

public class ResourceGroupServiceImpl implements ResourceGroupService {
    private ResourceGroupFinder resourceGroupFinder;
    private ResourceGroupModifier resourceGroupModifier;

    public ResourceGroupServiceImpl(ResourceGroupFinder resourceGroupFinder,
                                    ResourceGroupModifier resourceGroupModifier) {
        this.resourceGroupFinder = resourceGroupFinder;
        this.resourceGroupModifier = resourceGroupModifier;
    }

    @Override
    public List<ResourceGroup> getCurrentUserResourceGroups() {
        return resourceGroupFinder.getCurrentUserResourceGroups();
    }

    @Override
    public List<ResourceGroup> getUserResourceGroup(User user) {
        return resourceGroupFinder.getUserResourceGroup(user);
    }

    @Override
    public List<ResourceGroup> getUserResourceGroup(int userId) {
        return resourceGroupFinder.getUserResourceGroup(userId);
    }

    @Override
    public ResourceGroup getByNameWithDocuments(String name) {
        return resourceGroupFinder.getByNameWithDocuments(name);
    }

    @Override
    public ResourceGroup create(String name, String description) {
        return resourceGroupModifier.create(name, description);
    }
}
