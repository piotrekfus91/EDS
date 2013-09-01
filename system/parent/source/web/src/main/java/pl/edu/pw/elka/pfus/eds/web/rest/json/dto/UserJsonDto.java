package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;

public class UserJsonDto {
    private String name;
    private String friendlyName;

    public UserJsonDto() {

    }

    public UserJsonDto(String name, String friendlyName) {
        this.name = name;
        this.friendlyName = friendlyName;
    }

    public static UserJsonDto from(User user) {
        if(user == null)
            return new UserJsonDto();
        return new UserJsonDto(user.getName(), user.getFriendlyName());
    }
}
