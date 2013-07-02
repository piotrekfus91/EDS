package pl.edu.pw.elka.pfus.eds.domain.dao.factory;

import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.Date;

public class UserFactory implements EntityFactory<User> {
    private static int counter = 0;

    @Override
    public User getSampleEntity() {
        User user = new User();
        user.setName("login name" + counter++);
        user.setPasswordValue("");
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setCreated(new Date());
        return user;
    }
}
