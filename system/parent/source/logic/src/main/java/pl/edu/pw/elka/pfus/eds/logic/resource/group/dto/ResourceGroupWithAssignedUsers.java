package pl.edu.pw.elka.pfus.eds.logic.resource.group.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

/**
 * DTO przechowujące grupę zasobów, oraz użytkowników, którzy
 * mają jakiekolwiek uprawnienia.
 */
public class ResourceGroupWithAssignedUsers {
    private ResourceGroup resourceGroup;
    private List<User> users;

    public ResourceGroupWithAssignedUsers(ResourceGroup resourceGroup, List<User> users) {
        this.resourceGroup = resourceGroup;
        this.users = users;
    }

    public ResourceGroup getResourceGroup() {
        return resourceGroup;
    }

    public List<User> getUsers() {
        return users;
    }
}
