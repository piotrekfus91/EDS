package pl.edu.pw.elka.pfus.eds.security.privilege;

import org.apache.log4j.Logger;
import org.objectledge.security.DataBackend;
import org.objectledge.security.exception.DataBackendException;
import org.objectledge.security.object.Group;
import org.objectledge.security.object.Permission;
import org.objectledge.security.object.Role;
import org.objectledge.security.object.SecurityUser;
import org.objectledge.security.util.PermissionSet;
import org.objectledge.security.util.RoleSet;

import pl.edu.pw.elka.pfus.eds.security.exception.SecurityException;

import java.util.HashMap;
import java.util.Map;

public class PrivilegeServiceImpl implements PrivilegeService {
    private static final Logger logger = Logger.getLogger(PrivilegeServiceImpl.class);

    private DataBackend dataBackend;

    public PrivilegeServiceImpl(DataBackend dataBackend) {
        this.dataBackend = dataBackend;
    }

    @Override
    public boolean hasPrivilege(String userName, String privilegeName, String groupName) {
        logger.info("looking for privilege " + privilegeName + " for user " + userName +
                " over group " + groupName);
        try {
            Group group = dataBackend.getGroupByName(groupName);
            SecurityUser user = dataBackend.getUserByName(userName);
            RoleSet roles = dataBackend.getUserRoles(user, group);
            for(Role role : roles) {
                PermissionSet permissions = dataBackend.getPermissionsByRole(role);
                for(Permission permission : permissions) {
                    if(privilegeName.equals(permission.getName())) {
                        logger.info("found in role: " + role.getName());
                        return true;
                    }
                }
            }
            return false;
        } catch (DataBackendException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public Map<String, Boolean> getPrivilegesStatus(String userName, String groupName) {
        Map<String, Boolean> privilegesStatus = new HashMap<>();
        try {
            PermissionSet permissions = dataBackend.getAllPermissions();
            for(Permission permission : permissions) {
                privilegesStatus.put(permission.getName(), hasPrivilege(userName, permission.getName(), groupName));
            }
            return privilegesStatus;
        } catch (DataBackendException e) {
            throw new SecurityException(e);
        }
    }
}
