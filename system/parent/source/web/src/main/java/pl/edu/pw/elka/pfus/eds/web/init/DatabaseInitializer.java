package pl.edu.pw.elka.pfus.eds.web.init;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.picocontainer.Startable;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.web.init.impl.SqlScriptLoader;

import java.util.Date;

public class DatabaseInitializer implements Startable {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    private SessionFactory sessionFactory;
    private UserDao userDao;
    private DirectoryDao directoryDao;

    public DatabaseInitializer(SessionFactory sessionFactory, UserDao userDao, DirectoryDao directoryDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
        this.directoryDao = directoryDao;
    }

    @Override
    public void start() {
        logger.info("initializing database");
        initFromScripts();

        userDao.beginTransaction();
        User rootUser = new User();
        rootUser.setName("root");
        rootUser.setPasswordValue("asdf");
        rootUser.setEmail("root@localhost");
        rootUser.setFirstName("root");
        rootUser.setLastName("root");
        rootUser.setCreated(new Date());
        userDao.persist(rootUser);

        directoryDao.setSession(userDao.getSession());

        Directory rootDirectory = directoryDao.getRootDirectory(rootUser);

        Directory documentsDirectory = new Directory();
        documentsDirectory.setName("dokumenty");
        documentsDirectory.setParentDirectory(rootDirectory);
            Directory schoolDocumentsDirectory = new Directory();
            schoolDocumentsDirectory.setName("szkolne");
            schoolDocumentsDirectory.setParentDirectory(documentsDirectory);
                Directory pdiDirectory = new Directory();
                pdiDirectory.setName("PDI");
                pdiDirectory.setParentDirectory(schoolDocumentsDirectory);
                for(int i = 0; i < 7; i++) {
                    Directory semDirectory = new Directory();
                    semDirectory.setName("Semestr " + (i+1));
                    semDirectory.setParentDirectory(schoolDocumentsDirectory);
                }

        Directory picturesDirectory = new Directory();
        picturesDirectory.setName("obrazki");
        picturesDirectory.setParentDirectory(rootDirectory);
            Directory lfcPicturesDirectory = new Directory();
            lfcPicturesDirectory.setName("LFC");
            lfcPicturesDirectory.setParentDirectory(picturesDirectory);

        Directory filesDirectory = new Directory();
        filesDirectory.setName("pliki");
        filesDirectory.setParentDirectory(rootDirectory);
            Directory binariesDirectory = new Directory();
            binariesDirectory.setName("binarki");
            binariesDirectory.setParentDirectory(filesDirectory);

        directoryDao.persist(picturesDirectory);
        directoryDao.persist(documentsDirectory);
        directoryDao.persist(filesDirectory);
        directoryDao.commitTransaction();
    }

    @Override
    public void stop() {
        // nic sie nie powinno dziac
    }

    private void initFromScripts() {
        Session session = sessionFactory.getSession(null);
        logger.info("initializing security from ETL");
        session.getTransaction().begin();
        session.doWork(new SqlScriptLoader("/etl/security_inserts.sql"));
        session.doWork(new SqlScriptLoader("/etl/user_create_root_directory_after_insert_trigger.sql"));
        session.getTransaction().commit();
    }
}
