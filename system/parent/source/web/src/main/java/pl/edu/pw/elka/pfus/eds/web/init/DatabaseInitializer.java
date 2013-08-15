package pl.edu.pw.elka.pfus.eds.web.init;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.picocontainer.Startable;
import pl.edu.pw.elka.pfus.eds.domain.dao.CommentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.*;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.web.init.impl.SqlScriptLoader;

import java.util.Date;

public class DatabaseInitializer implements Startable {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    private SessionFactory sessionFactory;
    private UserDao userDao;
    private DirectoryDao directoryDao;
    private MimeTypeDao mimeTypeDao;
    private CommentDao commentDao;

    private MimeType jpegMimeType;

    public DatabaseInitializer(SessionFactory sessionFactory, UserDao userDao, DirectoryDao directoryDao,
                               MimeTypeDao mimeTypeDao, CommentDao commentDao) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
        this.directoryDao = directoryDao;
        this.mimeTypeDao = mimeTypeDao;
        this.commentDao = commentDao;
    }

    @Override
    public void start() {
        logger.info("initializing database");
        initFromScripts();

        initMimeTypes();

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
        mimeTypeDao.setSession(userDao.getSession());
        commentDao.setSession(userDao.getSession());

        jpegMimeType = mimeTypeDao.findById(jpegMimeType.getId());

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
                Document gerrard20 = new Document();
                gerrard20.setName("Gerrard20.jpeg");
                gerrard20.setCreated(new Date());
                gerrard20.setContentMd5("be4c71489e5964dcca00b6b3b3519631");
                gerrard20.setMimeType(jpegMimeType);
                jpegMimeType.addDocument(gerrard20);
                gerrard20.setDirectory(lfcPicturesDirectory);
                lfcPicturesDirectory.addDocument(gerrard20);
                Document lfc_226410 = new Document();
                lfc_226410.setName("lfc_226410.jpg");
                lfc_226410.setCreated(new Date());
                lfc_226410.setContentMd5("abbda56a02ca7d81fda888760d08127c");
                lfc_226410.setMimeType(jpegMimeType);
                jpegMimeType.addDocument(lfc_226410);
                lfc_226410.setDirectory(lfcPicturesDirectory);
                lfcPicturesDirectory.addDocument(lfc_226410);
                String[] commentsContent = new String[]{
                        "Bardzo ładny obrazek.",
                        "You'll never walk alone!",
                        "Fajne, skąd to masz?",
                        "Ja też taki chcę!",
                        "Ustawię sobie na tapecie."
                };
                for(int i = 0; i < commentsContent.length; i++) {
                    Comment comment = new Comment();
                    comment.setCreated(new Date());
                    comment.setContent(commentsContent[i]);
                    comment.setDocument(lfc_226410);
                    comment.setUser(rootUser);
                    commentDao.persist(comment);
                }

        Directory filesDirectory = new Directory();
        filesDirectory.setName("pliki");
        filesDirectory.setParentDirectory(rootDirectory);
            Directory binariesDirectory = new Directory();
            binariesDirectory.setName("binarki");
            binariesDirectory.setParentDirectory(filesDirectory);

        mimeTypeDao.setSession(directoryDao.getSession());
        mimeTypeDao.persist(jpegMimeType);
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

    private void initMimeTypes() {
        jpegMimeType = new MimeType();
        jpegMimeType.setDefaultExtension(".jpg");
        jpegMimeType.setType("image/jpeg");

        mimeTypeDao.beginTransaction();
        mimeTypeDao.persist(jpegMimeType);
        mimeTypeDao.commitTransaction();
    }
}
