package pl.edu.pw.elka.pfus.eds.web.init;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.objectledge.context.Context;
import org.picocontainer.Startable;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.web.init.impl.SqlScriptLoader;

import java.util.Date;

public class DatabaseInitializer implements Startable {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    private SessionFactory sessionFactory;
    private Context context;
    private UserDao userDao;

    public DatabaseInitializer(SessionFactory sessionFactory, Context context, UserDao userDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
    }

    @Override
    public void start() {
        logger.info("initializing database");
        initFromScripts();

        userDao.beginTransaction();
        User user = new User();
        user.setName("root");
        user.setPasswordValue("asdf");
        user.setEmail("root@localhost");
        user.setFirstName("root");
        user.setLastName("root");
        user.setCreated(new Date());
        userDao.persist(user);
        userDao.commitTransaction();
    }

    @Override
    public void stop() {
        // nic sie nie powinno dziac
    }

    private void initFromScripts() {
        Session session = sessionFactory.getSession(context);
        logger.info("initializing security from ETL");
        session.getTransaction().begin();
        session.doWork(new SqlScriptLoader("/etl/security_inserts.sql"));
        session.getTransaction().commit();
    }
}
