package pl.edu.pw.elka.pfus.eds.web.init;

import org.apache.log4j.Logger;
import org.picocontainer.Startable;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.Date;

public class DatabaseInitializer implements Startable {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    private UserDao userDao;

    public DatabaseInitializer(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void start() {
        logger.info("initializing database");
        User user = new User();
        user.setName("root");
        user.setPasswordValue("asdf");
        user.setEmail("root@localhost");
        user.setFirstName("root");
        user.setLastName("root");
        user.setCreated(new Date());
        userDao.persist(user);
    }

    @Override
    public void stop() {
        // nic sie nie powinno dziac
    }
}
