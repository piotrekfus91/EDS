package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.context.Context;
import org.objectledge.security.object.Group;
import org.objectledge.security.object.SecurityUser;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;
import pl.edu.pw.elka.pfus.eds.security.resource.group.ResourceGroupManager;
import pl.edu.pw.elka.pfus.eds.security.user.UserManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class SecurityFacadeImpl implements SecurityFacade {
    private UserManager userManager;
    private ResourceGroupManager resourceGroupManager;

    public SecurityFacadeImpl(UserManager userManager, ResourceGroupManager resourceGroupManager) {
        this.userManager = userManager;
        this.resourceGroupManager = resourceGroupManager;
    }

    @Override
    public void createUser(String login, String firstName, String lastName, String password) {
        userManager.createUser(login, firstName, lastName, password);
    }

    @Override
    public User logIn(Context context, String login, String password) {
        return userManager.logIn(context, login, password);
    }

    @Override
    public User getCurrentUser(Context context) {
        return userManager.getCurrentUser(context);
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        return userManager.getCurrentUser(request);
    }

    @Override
    public SecurityUser getCurrentSecurityUser(Context context) {
        return userManager.getCurrentSecurityUser(context);
    }

    @Override
    public boolean isLogged(Context context) {
        return userManager.isLogged(context);
    }

    @Override
    public List<RolesGrantedDto> getAvailableRolesForGroup(String groupName) {
        return resourceGroupManager.getAvailableRolesForGroup(groupName);
    }

    @Override
    public List<RolesGrantedDto> getUserRolesOverResourceGroup(String userName, String resourceGroupName) {
        return resourceGroupManager.getUserRolesOverResourceGroup(userName, resourceGroupName);
    }

    @Override
    public List<Group> getGroupsWhereUserHasAnyPrivilege(String userName) {
        return resourceGroupManager.getGroupsWhereUserHasAnyPrivilege(userName);
    }

    @Override
    public List<Group> getGroupsWhereUserHasPrivilege(String userName, String privilegeName) {
        return resourceGroupManager.getGroupsWhereUserHasPrivilege(userName, privilegeName);
    }

    @Override
    public void setUserRolesOnResourceGroup(String userName, String resourceGroupName, List<String> roleNames) {
        resourceGroupManager.setUserRolesOnResourceGroup(userName, resourceGroupName, roleNames);
    }

    @Override
    public void createResourceGroup(String name) {
        resourceGroupManager.createResourceGroup(name);
    }

    @Override
    public void renameResourceGroup(String oldName, String newName) {
        resourceGroupManager.renameResourceGroup(oldName, newName);
    }

    @Override
    public void deleteResourceGroup(String name) {
        resourceGroupManager.deleteResourceGroup(name);
    }

    @Override
    public List<SecurityUser> getAllUsersWithAnyPrivilegeOnResourceGroup(String resourceGroupName) {
        return resourceGroupManager.getAllUsersWithAnyPrivilegeOnResourceGroup(resourceGroupName);
    }
}
