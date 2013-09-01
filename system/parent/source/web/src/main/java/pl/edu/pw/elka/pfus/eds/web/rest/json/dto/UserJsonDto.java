package pl.edu.pw.elka.pfus.eds.web.rest.json.dto;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;

public class UserJsonDto {
    private String name;

    public UserJsonDto() {

    }

    public UserJsonDto(String name) {
        this.name = name;
    }

    public static UserJsonDto from(User user) {
        if(user == null)
            return new UserJsonDto();
        return new UserJsonDto(user.getFriendlyName());
    }
}
