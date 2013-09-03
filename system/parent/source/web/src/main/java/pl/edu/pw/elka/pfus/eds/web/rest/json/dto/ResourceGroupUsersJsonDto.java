package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResourceGroupUsersJsonDto {
    private ResourceGroupJsonDto resourceGroup;
    private List<UserJsonDto> users;
    private Map<String, Boolean> privilegesStatus;

    public ResourceGroupUsersJsonDto() {

    }

    public ResourceGroupUsersJsonDto(ResourceGroupJsonDto resourceGroup, List<UserJsonDto> users,
                                     Map<String, Boolean> privilegesStatus) {
        this.resourceGroup = resourceGroup;
        this.users = users;
        this.privilegesStatus = privilegesStatus;
    }

    public static ResourceGroupUsersJsonDto from(ResourceGroupWithAssignedUsers resourceGroupWithAssignedUsers) {
        if(resourceGroupWithAssignedUsers == null)
            return new ResourceGroupUsersJsonDto();
        List<UserJsonDto> users = new LinkedList<>();
        for(User user : resourceGroupWithAssignedUsers.getUsers())
            users.add(UserJsonDto.from(user));
        return new ResourceGroupUsersJsonDto(
                ResourceGroupJsonDto.from(resourceGroupWithAssignedUsers.getResourceGroup()), users,
                resourceGroupWithAssignedUsers.getPrivilegeStatus());
    }

    public ResourceGroupJsonDto getResourceGroup() {
        return resourceGroup;
    }

    public void setResourceGroup(ResourceGroupJsonDto resourceGroup) {
        this.resourceGroup = resourceGroup;
    }

    public List<UserJsonDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserJsonDto> users) {
        this.users = users;
    }

    public Map<String, Boolean> getPrivilegesStatus() {
        return privilegesStatus;
    }

    public void setPrivilegesStatus(Map<String, Boolean> privilegesStatus) {
        this.privilegesStatus = privilegesStatus;
    }
}
