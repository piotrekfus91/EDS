package pl.edu.pw.elka.pfus.eds.security.resource.group;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.security.DataBackend;
import org.objectledge.security.exception.DataBackendException;
import org.objectledge.security.object.Group;
import org.objectledge.security.object.Role;
import org.objectledge.security.object.SecurityUser;
import org.objectledge.security.util.GroupSet;
import org.objectledge.security.util.RoleSet;
import pl.edu.pw.elka.pfus.eds.security.dto.RolesGrantedDto;
import pl.edu.pw.elka.pfus.eds.security.exception.SecurityException;
import pl.edu.pw.elka.pfus.eds.security.privilege.PrivilegeService;
import pl.edu.pw.elka.pfus.eds.security.user.UserManager;
import pl.edu.pw.elka.pfus.eds.security.user.UserValidator;
import pl.edu.pw.elka.pfus.eds.util.config.Config;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResourceGroupManagerImpl implements ResourceGroupManager {
    private static final Logger logger = Logger.getLogger(ResourceGroupManagerImpl.class);

    private static final boolean REMOVE_OLD_ROLES = true;

    private Context context;
    private DataBackend dataBackend;
    private UserManager userManager;
    private PrivilegeService privilegeService;
    private UserValidator userValidator;
    private Config config;

    public ResourceGroupManagerImpl(Context context, DataBackend dataBackend, UserManager userManager,
                                    PrivilegeService privilegeService, UserValidator userValidator, Config config) {
        this.context = context;
        this.dataBackend = dataBackend;
        this.userManager = userManager;
        this.privilegeService = privilegeService;
        this.userValidator = userValidator;
        this.config = config;
    }

    @Override
    public void createResourceGroup(String name) {
        userValidator.enforceLogin(context);
        try {
            Group group = dataBackend.createGroup(name);
            dataBackend.saveGroup(group);
            group = dataBackend.getGroupByName(name);
            SecurityUser securityUser = userManager.getCurrentSecurityUser(context);
            Role adminRole = dataBackend.getRoleByName(config.getString("admin_role"));
            dataBackend.grant(securityUser, group, adminRole);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void renameResourceGroup(String oldName, String newName) {
        try {
            Group group = dataBackend.getGroupByName(oldName);
            group.setName(newName);
            dataBackend.saveGroup(group);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void deleteResourceGroup(String name) {
        try {
            Group group = dataBackend.getGroupByName(name);
            dataBackend.removeGroup(group);
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public List<RolesGrantedDto> getAvailableRolesForGroup(String groupName) {
        try {
            List<Role> allRoles = dataBackend.getAllRoles().getSortedList();
            List<RolesGrantedDto> rolesDtos = new LinkedList<>();
            for(Role role : allRoles) {
                rolesDtos.add(new RolesGrantedDto(role.getName(), false));
            }
            return rolesDtos;
        } catch (DataBackendException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public List<RolesGrantedDto> getUserRolesOverResourceGroup(String userName, String resourceGroupName) {
        try {
            SecurityUser user = dataBackend.getUserByName(userName);
            if(user == null)
                throw new SecurityException("Użytkownik " + userName + " nie istnieje");
            Group group = dataBackend.getGroupByName(resourceGroupName);
            if(group == null)
                throw new SecurityException("Grupa " + group + " nie istnieje");
            RoleSet roles = dataBackend.getUserRoles(user, group);
            List<Role> allRoles = dataBackend.getAllRoles().getSortedList();
            List<RolesGrantedDto> grantedRoles = new LinkedList<>();
            for(Role role : allRoles) {
                if(roles.contains(role))
                    grantedRoles.add(new RolesGrantedDto(role.getName(), true));
                else
                    grantedRoles.add(new RolesGrantedDto(role.getName(), false));
            }
            return grantedRoles;
        } catch (Exception e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public void setUserRolesOnResourceGroup(String userName, String resourceGroupName, List<String> roleNames) {
        try {
            SecurityUser user = dataBackend.getUserByName(userName);
            if(user == null)
                throw new SecurityException("Użytkownik " + userName + " nie istnieje");
            Group group = dataBackend.getGroupByName(resourceGroupName);
            if(group == null)
                throw new SecurityException("Grupa " + resourceGroupName + " nie istnieje");
            RoleSet newRoles = new RoleSet();
            for(String roleName : roleNames) {
                Role role = dataBackend.getRoleByName(roleName);
                if(role == null)
                    throw new SecurityException("Rola " + roleName + " nie istnieje");
                newRoles.add(role);
            }
            dataBackend.grant(user, group, newRoles, REMOVE_OLD_ROLES);
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

    @Override
    public List<Group> getGroupsWhereUserHasAnyPrivilege(String userName) {
        List<Group> userGroups = new LinkedList<>();
        try {
            GroupSet groups = dataBackend.getAllGroups();
            for(Group group : groups) {
                Map<String, Boolean> privileges = privilegeService.getPrivilegesStatus(userName, group.getName());
                if(hasAnyPrivilege(privileges)) {
                    userGroups.add(group);
                }
            }
            return userGroups;
        } catch (DataBackendException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public List<Group> getGroupsWhereUserHasPrivilege(String userName, String privilegeName) {
        List<Group> groupsWithPrivilege = new LinkedList<>();
        try {
            GroupSet groups = dataBackend.getAllGroups();
            for(Group group : groups) {
                if(privilegeService.hasPrivilege(userName, privilegeName, group.getName()))
                    groupsWithPrivilege.add(group);
            }
            return groupsWithPrivilege;
        } catch (DataBackendException e) {
            throw new SecurityException(e);
        }
    }

    private boolean hasAnyPrivilege(Map<String, Boolean> privileges) {
        for(Boolean hasPrivilege : privileges.values()) {
            if(hasPrivilege)
                return true;
        }
        return false;
    }
}