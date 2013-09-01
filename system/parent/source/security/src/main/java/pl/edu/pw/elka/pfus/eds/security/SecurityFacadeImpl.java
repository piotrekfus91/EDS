package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.context.Context;
import org.objectledge.security.object.SecurityUser;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
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
