package pl.edu.pw.elka.pfus.eds.logic.resource.group.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;
import java.util.Map;

/**
 * DTO przechowujące grupę zasobów, oraz użytkowników, którzy
 * mają jakiekolwiek uprawnienia.
 */
public class ResourceGroupWithAssignedUsers {
    private ResourceGroup resourceGroup;
    private List<User> users;
    private Map<String, Boolean> privilegeStatus;

    public ResourceGroupWithAssignedUsers(ResourceGroup resourceGroup, List<User> users,
                                          Map<String, Boolean> privilegeStatus) {
        this.resourceGroup = resourceGroup;
        this.users = users;
        this.privilegeStatus = privilegeStatus;
    }

    public ResourceGroup getResourceGroup() {
        return resourceGroup;
    }

    public List<User> getUsers() {
        return users;
    }

    public Map<String, Boolean> getPrivilegeStatus() {
        return privilegeStatus;
    }
}
