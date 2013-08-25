package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupModifier;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

public class ResourceGroupModifierImpl implements ResourceGroupModifier {
    private static final Logger logger = Logger.getLogger(ResourceGroupModifierImpl.class);

    private Context context;
    private SecurityFacade securityFacade;
    private ResourceGroupDao resourceGroupDao;
    private UserDao userDao;

    public ResourceGroupModifierImpl(Context context, SecurityFacade securityFacade,
                                     ResourceGroupDao resourceGroupDao, UserDao userDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.resourceGroupDao = resourceGroupDao;
        this.userDao = userDao;
    }

    @Override
    public ResourceGroup create(String name, String description) {
        User currentUser = securityFacade.getCurrentUser(context);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.setName(name);
        resourceGroup.setDescription(description);
        resourceGroup.setFounder(userDao.findById(currentUser.getId()));

        try {
            resourceGroupDao.beginTransaction();
            resourceGroupDao.persist(resourceGroup);
            resourceGroupDao.commitTransaction();
            return resourceGroup;
        } catch (Exception e) {
            resourceGroupDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    @Override
    public ResourceGroup updateNameAndDescription(String oldName, String newName, String description) {
        userDao.setSession(resourceGroupDao.getSession());
        ResourceGroup resourceGroup = resourceGroupDao.findByName(oldName);
        LogicValidator.validateExistence(resourceGroup);

        User currentUser = securityFacade.getCurrentUser(context);
        LogicValidator.validateOwnershipOverResourceGroup(currentUser, resourceGroup);

        try {
            resourceGroupDao.beginTransaction();
            resourceGroup.setName(newName);
            resourceGroup.setDescription(description);
            resourceGroup.setFounder(userDao.findById(currentUser.getId()));
            resourceGroup = resourceGroupDao.merge(resourceGroup);
            resourceGroupDao.commitTransaction();
            return resourceGroup;
        } catch (Exception e) {
            resourceGroupDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }
}