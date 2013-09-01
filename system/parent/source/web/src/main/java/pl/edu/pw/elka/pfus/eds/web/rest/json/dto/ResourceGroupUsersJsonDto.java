package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;

import java.util.LinkedList;
import java.util.List;

public class ResourceGroupUsersJsonDto {
    private ResourceGroupJsonDto resourceGroup;
    private List<UserJsonDto> users;

    public ResourceGroupUsersJsonDto() {

    }

    public ResourceGroupUsersJsonDto(ResourceGroupJsonDto resourceGroup, List<UserJsonDto> users) {
        this.resourceGroup = resourceGroup;
        this.users = users;
    }

    public static ResourceGroupUsersJsonDto from(ResourceGroupWithAssignedUsers resourceGroupWithAssignedUsers) {
        if(resourceGroupWithAssignedUsers == null)
            return new ResourceGroupUsersJsonDto();
        List<UserJsonDto> users = new LinkedList<>();
        for(User user : resourceGroupWithAssignedUsers.getUsers())
            users.add(UserJsonDto.from(user));
        return new ResourceGroupUsersJsonDto(
                ResourceGroupJsonDto.from(resourceGroupWithAssignedUsers.getResourceGroup()), users);
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
}
