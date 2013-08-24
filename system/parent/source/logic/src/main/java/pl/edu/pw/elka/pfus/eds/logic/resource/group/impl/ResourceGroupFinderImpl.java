package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupFinder;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.List;

public class ResourceGroupFinderImpl implements ResourceGroupFinder {
    private Context context;
    private SecurityFacade securityFacade;
    private ResourceGroupDao resourceGroupDao;

    public ResourceGroupFinderImpl(Context context, SecurityFacade securityFacade, ResourceGroupDao resourceGroupDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.resourceGroupDao = resourceGroupDao;
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
}
