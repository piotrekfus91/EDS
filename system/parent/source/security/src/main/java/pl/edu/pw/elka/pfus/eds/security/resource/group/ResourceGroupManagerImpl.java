package pl.edu.pw.elka.pfus.eds.security.resource.group;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.security.DataBackend;
import org.objectledge.security.exception.EntityExistsException;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.security.user.UserManager;
import pl.edu.pw.elka.pfus.eds.security.user.UserValidator;

import java.util.List;

public class ResourceGroupManagerImpl implements ResourceGroupManager {
    private static final Logger logger = Logger.getLogger(ResourceGroupManagerImpl.class);

    private Context context;
    private DataBackend dataBackend;
    private ResourceGroupDao resourceGroupDao;
    private UserManager userManager;
    private UserValidator userValidator;

    public ResourceGroupManagerImpl(Context context, DataBackend dataBackend, ResourceGroupDao resourceGroupDao,
                                    UserManager userManager, UserValidator userValidator) {
        this.context = context;
        this.dataBackend = dataBackend;
        this.resourceGroupDao = resourceGroupDao;
        this.userManager = userManager;
        this.userValidator = userValidator;
    }

    @Override
    public void createResourceGroup(String name) {
        userValidator.enforceLogin(context);

        try {
            dataBackend.createGroup(name);
        } catch (EntityExistsException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public List<ResourceGroup> getGroupsFoundedByCurrentUser() {
        userValidator.enforceLogin(context);
        User currentUser = userManager.getCurrentUser(context);
        return getGroupsFoundedByUser(currentUser.getName());
    }

    @Override
    public List<ResourceGroup> getGroupsFoundedByUser(User user) {
        return getGroupsFoundedByUser(user.getName());
    }

    @Override
    public List<ResourceGroup> getGroupsFoundedByUser(String login) {
        return null;
    }
}
