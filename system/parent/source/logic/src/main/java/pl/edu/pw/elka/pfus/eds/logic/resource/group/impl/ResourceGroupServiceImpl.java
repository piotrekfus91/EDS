package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupFinder;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupModifier;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupService;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.domain.dao.dto.SharedResourceGroupDto;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;

import java.util.List;
import java.util.Map;

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
    public ResourceGroupWithAssignedUsers getByNameWithUsers(String name) {
        return resourceGroupFinder.getByNameWithUsers(name);
    }

    @Override
    public List<SharedResourceGroupDto> getSharableGroupsForCurrentUserAndDocument(int documentId) {
        return resourceGroupFinder.getSharableGroupsForCurrentUserAndDocument(documentId);
    }

    @Override
    public List<SharedResourceGroupDto> getSharableGroupsForCurrentUserAndDirectory(int directoryId) {
        return resourceGroupFinder.getSharableGroupsForCurrentUserAndDirectory(directoryId);
    }

    @Override
    public List<RolesGrantedDto> getAvailableRolesForGroup(String groupName) {
        return resourceGroupFinder.getAvailableRolesForGroup(groupName);
    }

    @Override
    public List<RolesGrantedDto> getUserRolesOverResourceGroups(String userName, String resourceGroupName) {
        return resourceGroupFinder.getUserRolesOverResourceGroups(userName, resourceGroupName);
    }

    @Override
    public List<ResourceGroup> getGroupsWhereCurrentUserHasAnyPrivilege() {
        return resourceGroupFinder.getGroupsWhereCurrentUserHasAnyPrivilege();
    }

    @Override
    public ResourceGroup create(String name, String description) {
        return resourceGroupModifier.create(name, description);
    }

    @Override
    public ResourceGroup updateNameAndDescription(String oldName, String newName, String description) {
        return resourceGroupModifier.updateNameAndDescription(oldName, newName, description);
    }

    @Override
    public void updateRoles(String groupName, String userName, List<RolesGrantedDto> rolesGranted) {
        resourceGroupModifier.updateRoles(groupName, userName, rolesGranted);
    }

    @Override
    public void updateDocumentPublishing(int documentId, Map<String, Boolean> sharedInGroups) {
        resourceGroupModifier.updateDocumentPublishing(documentId, sharedInGroups);
    }

    @Override
    public void updateDirectoryPublishing(int directoryId, Map<String, Boolean> sharedInGroups) {
        resourceGroupModifier.updateDirectoryPublishing(directoryId, sharedInGroups);
    }

    @Override
    public void delete(String name) {
        resourceGroupModifier.delete(name);
    }
}
