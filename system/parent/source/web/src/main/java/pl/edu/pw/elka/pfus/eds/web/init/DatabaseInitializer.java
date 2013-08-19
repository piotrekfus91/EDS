package pl.edu.pw.elka.pfus.eds.web.init;

import com.google.common.io.ByteStreams;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.picocontainer.Startable;
import pl.edu.pw.elka.pfus.eds.domain.dao.*;
import pl.edu.pw.elka.pfus.eds.domain.entity.*;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.logic.tag.cache.TagCache;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;
import pl.edu.pw.elka.pfus.eds.util.file.system.PathCreator;
import pl.edu.pw.elka.pfus.eds.web.init.impl.SqlScriptLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class DatabaseInitializer implements Startable {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class);

    private SessionFactory sessionFactory;
    private UserDao userDao;
    private DirectoryDao directoryDao;
    private MimeTypeDao mimeTypeDao;
    private CommentDao commentDao;
    private TagDao tagDao;
    private FileManager fileManager;
    private PathCreator pathCreator;
    private TagCache tagCache;

    private MimeType jpegMimeType;

    private Tag wallpaperTag;
    private Tag lfcTag;
    private Tag scholarTag;
    private Tag polishTag;

    public DatabaseInitializer(SessionFactory sessionFactory, UserDao userDao, DirectoryDao directoryDao,
                               MimeTypeDao mimeTypeDao, CommentDao commentDao, TagDao tagDao, FileManager fileManager,
                               PathCreator pathCreator, TagCache tagCache) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
        this.directoryDao = directoryDao;
        this.mimeTypeDao = mimeTypeDao;
        this.commentDao = commentDao;
        this.tagDao = tagDao;
        this.fileManager = fileManager;
        this.pathCreator = pathCreator;
        this.tagCache = tagCache;
        tagCache.setSession(userDao.getSession());
    }

    @Override
    public void start() {
        logger.info("initializing database");
        initFromScripts();

        initMimeTypes();
        initTags();

        pathCreator.createFileSystemRoot();

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
        tagDao.setSession(userDao.getSession());

        jpegMimeType = mimeTypeDao.findById(jpegMimeType.getId());
        lfcTag = tagDao.findById(lfcTag.getId());
        wallpaperTag = tagDao.findById(wallpaperTag.getId());

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
                InputStream gerrard20IS = DatabaseInitializer.class.getClassLoader().getResourceAsStream("documents/Gerrard20.jpeg");
                try {
                    byte[] gerrard20Bytes = ByteStreams.toByteArray(gerrard20IS);
                    fileManager.create(gerrard20Bytes, gerrard20.getFileSystemName());
                } catch (IOException e) {
                    throw new ExceptionInInitializerError(e);
                }
                gerrard20.addTag(lfcTag);
                lfcTag.addDocument(gerrard20);
                gerrard20.addTag(wallpaperTag);
                wallpaperTag.addDocument(gerrard20);
                gerrard20.addTag(polishTag);
                polishTag.addDocument(gerrard20);
                Document lfc_226410 = new Document();
                lfc_226410.setName("lfc_226410.jpg");
                lfc_226410.setCreated(new Date());
                lfc_226410.setContentMd5("abbda56a02ca7d81fda888760d08127c");
                lfc_226410.setMimeType(jpegMimeType);
                jpegMimeType.addDocument(lfc_226410);
                lfc_226410.setDirectory(lfcPicturesDirectory);
                lfcPicturesDirectory.addDocument(lfc_226410);
                InputStream lfc_226410IS = DatabaseInitializer.class.getClassLoader().getResourceAsStream("documents/lfc_226410.jpg");
                try {
                    byte[] lfc_22610Bytes = ByteStreams.toByteArray(lfc_226410IS);
                    fileManager.create(lfc_22610Bytes, lfc_226410.getFileSystemName());
                } catch (IOException e) {
                    throw new ExceptionInInitializerError(e);
                }
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
                lfc_226410.addTag(lfcTag);
                lfcTag.addDocument(lfc_226410);

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

        tagCache.rebuild();
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

    private void initTags() {
        wallpaperTag = new Tag();
        wallpaperTag.setValue("tapety");

        lfcTag = new Tag();
        lfcTag.setValue("LFC");

        scholarTag = new Tag();
        scholarTag.setValue("szkolne");

        polishTag = new Tag();
        polishTag.setValue("zażółć GĘŚLĄ jaźń");

        tagDao.beginTransaction();
        tagDao.persist(wallpaperTag);
        tagDao.persist(lfcTag);
        tagDao.persist(scholarTag);
        tagDao.persist(polishTag);
        tagDao.commitTransaction();
    }
}
