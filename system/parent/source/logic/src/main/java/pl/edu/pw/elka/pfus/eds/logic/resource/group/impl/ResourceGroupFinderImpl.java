package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.objectledge.context.Context;
import org.objectledge.security.object.SecurityUser;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupFinder;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;

import java.util.LinkedList;
import java.util.List;

public class ResourceGroupFinderImpl implements ResourceGroupFinder {
    private Context context;
    private SecurityFacade securityFacade;
    private ResourceGroupDao resourceGroupDao;
    private UserDao userDao;

    public ResourceGroupFinderImpl(Context context, SecurityFacade securityFacade, ResourceGroupDao resourceGroupDao,
                                   UserDao userDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.resourceGroupDao = resourceGroupDao;
        this.userDao = userDao;
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

        return new ResourceGroupWithAssignedUsers(resourceGroup, users);
    }

    @Override
    public List<RolesGrantedDto> getUserRolesOverResourceGroups(String userName, String resourceGroupName) {
        return securityFacade.getUserRolesOverResourceGroup(userName, resourceGroupName);
    }
}
