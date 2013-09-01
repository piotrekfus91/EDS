package pl.edu.pw.elka.pfus.eds.security.resource.group;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.security.DataBackend;
import org.objectledge.security.exception.DataBackendException;
import org.objectledge.security.object.Group;
import org.objectledge.security.object.Role;
import org.objectledge.security.object.SecurityUser;
import org.objectledge.security.util.RoleSet;
import pl.edu.pw.elka.pfus.eds.security.user.UserManager;
import pl.edu.pw.elka.pfus.eds.security.user.UserValidator;

import java.util.LinkedList;
import java.util.List;

public class ResourceGroupManagerImpl implements ResourceGroupManager {
    private static final Logger logger = Logger.getLogger(ResourceGroupManagerImpl.class);

    private Context context;
    private DataBackend dataBackend;
    private UserManager userManager;
    private UserValidator userValidator;

    public ResourceGroupManagerImpl(Context context, DataBackend dataBackend, UserManager userManager,
                                    UserValidator userValidator) {
        this.context = context;
        this.dataBackend = dataBackend;
        this.userManager = userManager;
        this.userValidator = userValidator;
    }

    @Override
    public void createResourceGroup(String name) {
        userValidator.enforceLogin(context);
        try {
            Group group = dataBackend.createGroup(name);
            SecurityUser securityUser = userManager.getCurrentSecurityUser(context);
            Role adminRole = dataBackend.getRoleByName("Admin");
            dataBackend.grant(securityUser, group, adminRole);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public List<SecurityUser> getAllUsersWithAnyPrivilegeOnResourceGroup(String resourceGroupName) {
        List<SecurityUser> result = new LinkedList<>();
        try {
            Group group = dataBackend.getGroupByName(resourceGroupName);
            for(SecurityUser user : dataBackend.getAllUsers()) {
                RoleSet roles = dataBackend.getUserRoles(user, group);
                if(!roles.isEmpty()) {
                    result.add(user);
                }
            }
            return result;
        } catch (DataBackendException e) {
            throw new SecurityException(e);
        }
    }
}