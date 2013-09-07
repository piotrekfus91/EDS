package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import com.google.common.collect.ImmutableList;
import org.objectledge.context.Context;
import org.objectledge.security.object.Group;
import org.objectledge.security.object.SecurityUser;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.dto.SharedResourceGroupDto;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupFinder;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;
import pl.edu.pw.elka.pfus.eds.security.privilege.PrivilegeService;
import pl.edu.pw.elka.pfus.eds.security.privilege.Privileges;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResourceGroupFinderImpl implements ResourceGroupFinder {
    private Context context;
    private SecurityFacade securityFacade;
    private PrivilegeService privilegeService;
    private ResourceGroupDao resourceGroupDao;
    private UserDao userDao;
    private DocumentDao documentDao;

    public ResourceGroupFinderImpl(Context context, SecurityFacade securityFacade, PrivilegeService privilegeService,
                                   ResourceGroupDao resourceGroupDao, UserDao userDao, DocumentDao documentDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.privilegeService = privilegeService;
        this.resourceGroupDao = resourceGroupDao;
        this.userDao = userDao;
        this.documentDao = documentDao;
    }

    @Override
    public List<ResourceGroup> getCurrentUserResourceGroups() {
        User currentUser = securityFacade.getCurrentUser(context);
        return getUserResourceGroup(currentUser);
    }

    @Override
    public List<ResourceGroup> getUserResourceGroup(User user) {
        return getUserResourceGroup(user.getId());
    }

    @Override
    public List<ResourceGroup> getUserResourceGroup(int userId) {
        return resourceGroupDao.getAllOfFounder(userId);
    }

    @Override
    public ResourceGroupWithAssignedUsers getByNameWithUsers(String name) {
        ResourceGroup resourceGroup = resourceGroupDao.findByName(name);
        LogicValidator.validateExistence(resourceGroup);

        resourceGroup.getAllDocuments();

        List<User> users = new LinkedList<>();
        List<SecurityUser> securityUsers = securityFacade.getAllUsersWithAnyPrivilegeOnResourceGroup(name);
        for(SecurityUser securityUser : securityUsers) {
            User user = userDao.findByName(securityUser.getName());
            users.add(user);
        }

        User currentUser = securityFacade.getCurrentUser(context);
        String userName = currentUser.getName();
        Map<String, Boolean> currentUserPermissionStatus = privilegeService.getPrivilegesStatus(userName, name);

        return new ResourceGroupWithAssignedUsers(resourceGroup, users, currentUserPermissionStatus);
    }

    @Override
    public List<SharedResourceGroupDto> getSharableGroupsForCurrentUserAndDocument(int documentId) {
        User currentUser = securityFacade.getCurrentUser(context);
        return getSharableGroupsForUser(currentUser.getName(), documentId);
    }

    private List<SharedResourceGroupDto> getSharableGroupsForUser(String userName, int documentId) {
        List<Group> securityGroups = securityFacade.getGroupsWhereUserHasPrivilege(userName, Privileges.SHARE_FILES);
        List<String> groupNames = getGroupNames(securityGroups);
        List<SharedResourceGroupDto> sharedResourceGroupDtos = new LinkedList<>();
        if(!groupNames.isEmpty()) {
            List<ResourceGroup> resourceGroups = resourceGroupDao.getResourceGroupsWithNames(groupNames);
            Document document = documentDao.findById(documentId);
            for(ResourceGroup resourceGroup : resourceGroups) {
                boolean shared = resourceGroup.getDocuments().contains(document);
                sharedResourceGroupDtos.add(new SharedResourceGroupDto(resourceGroup, shared));
            }
        }
        return ImmutableList.copyOf(sharedResourceGroupDtos);
    }

    private List<String> getGroupNames(List<Group> securityGroups) {
        List<String> groupNames = new LinkedList<>();
        for(Group group : securityGroups) {
            groupNames.add(group.getName());
        }
        return groupNames;
    }

    @Override
    public List<RolesGrantedDto> getUserRolesOverResourceGroups(String userName, String resourceGroupName) {
        return securityFacade.getUserRolesOverResourceGroup(userName, resourceGroupName);
    }

    @Override
    public List<ResourceGroup> getGroupsWhereCurrentUserHasAnyPrivilege() {
        User currentUser = securityFacade.getCurrentUser(context);
        List<Group> securityGroups =  securityFacade.getGroupsWhereUserHasAnyPrivilege(currentUser.getName());
        List<ResourceGroup> resourceGroups = new LinkedList<>();
        for(Group securityGroup : securityGroups) {
            ResourceGroup resourceGroup = resourceGroupDao.findByName(securityGroup.getName());
            resourceGroups.add(resourceGroup);
        }
        return ImmutableList.copyOf(resourceGroups);
    }
}
