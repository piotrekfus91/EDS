package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.ResourceGroupModifier;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;
import pl.edu.pw.elka.pfus.eds.security.exception.SecurityException;
import pl.edu.pw.elka.pfus.eds.security.privilege.PrivilegeService;
import pl.edu.pw.elka.pfus.eds.security.privilege.Privileges;

import java.util.List;
import java.util.Map;

import static pl.edu.pw.elka.pfus.eds.logic.error.handler.ErrorHandler.handle;

public class ResourceGroupModifierImpl implements ResourceGroupModifier {
    private static final Logger logger = Logger.getLogger(ResourceGroupModifierImpl.class);

    private Context context;
    private SecurityFacade securityFacade;
    private PrivilegeService privilegeService;
    private ResourceGroupDao resourceGroupDao;
    private UserDao userDao;
    private DocumentDao documentDao;
    private DirectoryDao directoryDao;

    public ResourceGroupModifierImpl(Context context, SecurityFacade securityFacade, PrivilegeService privilegeService,
                                     ResourceGroupDao resourceGroupDao, UserDao userDao, DocumentDao documentDao,
                                     DirectoryDao directoryDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.privilegeService = privilegeService;
        this.resourceGroupDao = resourceGroupDao;
        this.userDao = userDao;
        this.documentDao = documentDao;
        this.directoryDao = directoryDao;
    }

    @Override
    public ResourceGroup create(String name, String description) {
        resourceGroupDao.clear();
        User currentUser = securityFacade.getCurrentUser(context);
        ResourceGroup resourceGroup = new ResourceGroup();
        resourceGroup.setName(name);
        resourceGroup.setDescription(description);
        resourceGroup.setFounder(userDao.findById(currentUser.getId()));

        try {
            resourceGroupDao.beginTransaction();
            resourceGroupDao.persist(resourceGroup);
            securityFacade.createResourceGroup(name);
            resourceGroupDao.commitTransaction();
            return resourceGroup;
        } catch (Exception e) {
            handle(e, resourceGroupDao);
            throw new InternalException();
        }
    }

    @Override
    public ResourceGroup updateNameAndDescription(String oldName, String newName, String description) {
        resourceGroupDao.clear();
        userDao.setSession(resourceGroupDao.getSession());
        ResourceGroup resourceGroup = resourceGroupDao.findByName(oldName);
        LogicValidator.validateExistence(resourceGroup);

        User currentUser = securityFacade.getCurrentUser(context);
        if(!privilegeService.hasPrivilege(currentUser.getName(), Privileges.UPDATE_INFO, oldName))
            throw new InvalidPrivilegesException();

        try {
            resourceGroupDao.beginTransaction();
            securityFacade.renameResourceGroup(oldName, newName);
            resourceGroup.setName(newName);
            resourceGroup.setDescription(description);
            resourceGroup.setFounder(userDao.findById(currentUser.getId()));
            resourceGroup = resourceGroupDao.merge(resourceGroup);
            resourceGroupDao.commitTransaction();
            return resourceGroup;
        } catch (Exception e) {
            handle(e, resourceGroupDao);
            throw new InternalException();
        }
    }

    @Override
    public void updateRoles(String groupName, String userName, List<RolesGrantedDto> rolesGranted) {
        resourceGroupDao.clear();
        User currentUser = securityFacade.getCurrentUser(context);
        if(!privilegeService.hasPrivilege(currentUser.getName(), Privileges.MANAGE_ROLES, groupName))
            throw new InvalidPrivilegesException();

        try {
            for(RolesGrantedDto roleGranted : rolesGranted) {
                if(roleGranted.isHas()) {
                    securityFacade.grantRoleToUserOverResourceGroup(userName, roleGranted.getRoleName(), groupName);
                } else {
                    securityFacade.revokeRoleFromUserOverResourceGroup(userName, roleGranted.getRoleName(), groupName);
                }
            }
        } catch (SecurityException e) {
            throw new LogicException(e.getMessage());
        }
    }

    @Override
    public void updateDocumentPublishing(int documentId, Map<String, Boolean> sharedInGroups) {
        documentDao.setSession(resourceGroupDao.getSession());
        directoryDao.setSession(resourceGroupDao.getSession());
        resourceGroupDao.clear();
        Document document = documentDao.findById(documentId);
        User currentUser = securityFacade.getCurrentUser(context);
        List<Integer> groupsIds = resourceGroupDao.getIdsOfNames(Lists.newLinkedList(sharedInGroups.keySet()));
        try {
            resourceGroupDao.beginTransaction();
            for(Integer groupId : groupsIds) {
                ResourceGroup resourceGroup = resourceGroupDao.findById(groupId);
                String groupName = resourceGroup.getName();
                if(canShare(currentUser, groupName)) {
                    if(sharedInGroups.get(groupName)) {
                        if(!resourceGroup.getDocuments().contains(document)) {
                            document.addResourceGroup(resourceGroup);
                            resourceGroup.addDocument(document);
                        }
                    } else {
                        resourceGroup.removeDocument(document);
                        document.removeResourceGroup(resourceGroup);
                    }
                    resourceGroupDao.persist(resourceGroup);
                    documentDao.persist(document);
                } else {
                    throw new InvalidPrivilegesException();
                }
            }
            resourceGroupDao.commitTransaction();
        } catch (Exception e) {
            handle(e, resourceGroupDao);
            throw new InternalException();
        }
    }

    @Override
    public void updateDirectoryPublishing(int directoryId, Map<String, Boolean> sharedInGroups) {
        documentDao.setSession(resourceGroupDao.getSession());
        directoryDao.setSession(resourceGroupDao.getSession());
        resourceGroupDao.clear();
        Directory directory = directoryDao.findById(directoryId);
        User currentUser = securityFacade.getCurrentUser(context);
        List<Integer> groupsIds = resourceGroupDao.getIdsOfNames(Lists.newLinkedList(sharedInGroups.keySet()));
        try {
            resourceGroupDao.beginTransaction();
            for(Integer groupId : groupsIds) {
                ResourceGroup resourceGroup = resourceGroupDao.findById(groupId);
                String groupName = resourceGroup.getName();
                if(canShare(currentUser, groupName)) {
                    if(sharedInGroups.get(groupName)) {
                        if(!resourceGroup.getDirectories().contains(directory)) {
                            resourceGroup.addDirectory(directory);
                            directory.addResourceGroup(resourceGroup);
                        }
                    } else {
                        resourceGroup.removeDirectory(directory);
                        directory.removeResourceGroup(resourceGroup);
                    }
                } else {
                    throw new InvalidPrivilegesException();
                }
                resourceGroupDao.persist(resourceGroup);
                directoryDao.persist(directory);
            }
            resourceGroupDao.commitTransaction();
        } catch (Exception e) {
            handle(e, resourceGroupDao);
            throw new InternalException();
        }
    }

    private boolean canShare(User currentUser, String groupName) {
        return privilegeService.hasPrivilege(currentUser.getName(), Privileges.SHARE_FILES, groupName);
    }

    @Override
    public void delete(String name) {
        resourceGroupDao.clear();
        ResourceGroup resourceGroup = resourceGroupDao.findByName(name);
        LogicValidator.validateExistence(resourceGroup);

        User currentUser = securityFacade.getCurrentUser(context);
        if(!privilegeService.hasPrivilege(currentUser.getName(), Privileges.DELETE, name))
            throw new InvalidPrivilegesException();

        try {
            resourceGroupDao.beginTransaction();
            securityFacade.deleteResourceGroup(name);
            resourceGroup = resourceGroupDao.findById(resourceGroup.getId());
            resourceGroupDao.delete(resourceGroup);
            resourceGroupDao.commitTransaction();
        } catch (Exception e) {
            handle(e, resourceGroupDao);
            throw new InternalException();
        }
    }
}