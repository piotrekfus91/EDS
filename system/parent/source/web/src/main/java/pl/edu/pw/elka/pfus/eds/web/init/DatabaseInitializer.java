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
    private ResourceGroupDao resourceGroupDao;
    private FileManager fileManager;
    private PathCreator pathCreator;
    private TagCache tagCache;

    private MimeType jpegMimeType;
    private MimeType pdfMimeType;
    private MimeType docMimeType;
    private MimeType plainTextMimeType;

    private Tag wallpaperTag;
    private Tag lfcTag;
    private Tag scholarTag;
    private Tag thesisTag;

    public DatabaseInitializer(SessionFactory sessionFactory, UserDao userDao, DirectoryDao directoryDao,
                               MimeTypeDao mimeTypeDao, CommentDao commentDao, TagDao tagDao,
                               ResourceGroupDao resourceGroupDao, FileManager fileManager, PathCreator pathCreator,
                               TagCache tagCache) {
        this.sessionFactory = sessionFactory;
        this.userDao = userDao;
        this.directoryDao = directoryDao;
        this.mimeTypeDao = mimeTypeDao;
        this.commentDao = commentDao;
        this.tagDao = tagDao;
        this.resourceGroupDao = resourceGroupDao;
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
        rootUser.setFirstName("Piotrek");
        rootUser.setLastName("Fus");
        rootUser.setCreated(new Date());

        User johnnyUser = new User();
        johnnyUser.setName("johnny");
        johnnyUser.setPasswordValue("asdf");
        johnnyUser.setEmail("johnny@localhost");
        johnnyUser.setFirstName("Johnny");
        johnnyUser.setLastName("Opalony");
        johnnyUser.setCreated(new Date());

        User jerryUser = new User();
        jerryUser.setName("jerry");
        jerryUser.setPasswordValue("asdf");
        jerryUser.setEmail("jerry@localhost");
        jerryUser.setFirstName("Jurek");
        jerryUser.setLastName("Ogórek");
        jerryUser.setCreated(new Date());

        userDao.persist(rootUser);
        userDao.persist(johnnyUser);
        userDao.persist(jerryUser);
        userDao.commitTransaction();

        userDao.beginTransaction();

        directoryDao.setSession(userDao.getSession());
        mimeTypeDao.setSession(userDao.getSession());
        commentDao.setSession(userDao.getSession());
        tagDao.setSession(userDao.getSession());
        resourceGroupDao.setSession(userDao.getSession());

        jpegMimeType = mimeTypeDao.findById(jpegMimeType.getId());
        pdfMimeType = mimeTypeDao.findById(pdfMimeType.getId());
        docMimeType = mimeTypeDao.findById(docMimeType.getId());
        plainTextMimeType = mimeTypeDao.findById(plainTextMimeType.getId());
        lfcTag = tagDao.findById(lfcTag.getId());
        wallpaperTag = tagDao.findById(wallpaperTag.getId());
        thesisTag = tagDao.findById(thesisTag.getId());

        Directory rootDirectory = directoryDao.getRootDirectory(rootUser);

        Directory documentsDirectory = new Directory();
        documentsDirectory.setName("dokumenty");
        documentsDirectory.setParentDirectory(rootDirectory);
            Document polishDocument = new Document();
            polishDocument.setName("Plan-polski.doc");
            polishDocument.setCreated(new Date());
            polishDocument.setContentMd5("3779b2ec5fddd49aefb6fdf18b394bc8");
            polishDocument.setMimeType(docMimeType);
            polishDocument.setOwner(rootUser);
            polishDocument.setDirectory(documentsDirectory);
            documentsDirectory.addDocument(polishDocument);
            saveDocumentToRepository(polishDocument);
            Directory schoolDocumentsDirectory = new Directory();
            schoolDocumentsDirectory.setName("szkolne");
            schoolDocumentsDirectory.setParentDirectory(documentsDirectory);
                Directory pdiDirectory = new Directory();
                pdiDirectory.setName("PDI");
                pdiDirectory.setParentDirectory(schoolDocumentsDirectory);
                    Document thesisDocument = new Document();
                    thesisDocument.setName("Praca.pdf");
                    thesisDocument.setOwner(rootUser);
                    thesisDocument.setCreated(new Date());
                    thesisDocument.setContentMd5("e78b47846e48ed63ca58794b65701c53");
                    thesisDocument.setMimeType(pdfMimeType);
                    thesisDocument.addTag(thesisTag);
                    thesisDocument.setOwner(rootUser);
                    thesisTag.addDocument(thesisDocument);
                    thesisDocument.addTag(scholarTag);
                    scholarTag.addDocument(thesisDocument);
                    pdiDirectory.addDocument(thesisDocument);
                    thesisDocument.setDirectory(pdiDirectory);
                    saveDocumentToRepository(thesisDocument);
                    Document technologiesDocument = new Document();
                    technologiesDocument.setName("technologie.tex");
                    technologiesDocument.setCreated(new Date());
                    technologiesDocument.setOwner(rootUser);
                    technologiesDocument.setContentMd5("693324fdccbb70a358591f764cbf3400");
                    technologiesDocument.setMimeType(plainTextMimeType);
                    technologiesDocument.setOwner(rootUser);
                    technologiesDocument.addTag(thesisTag);
                    thesisTag.addDocument(technologiesDocument);
                    technologiesDocument.addTag(scholarTag);
                    scholarTag.addDocument(technologiesDocument);
                    technologiesDocument.setDirectory(pdiDirectory);
                    pdiDirectory.addDocument(technologiesDocument);
                    saveDocumentToRepository(thesisDocument);
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
                saveDocumentToRepository(gerrard20);
                gerrard20.addTag(lfcTag);
                lfcTag.addDocument(gerrard20);
                gerrard20.addTag(wallpaperTag);
                wallpaperTag.addDocument(gerrard20);
                Document lfc_226410 = new Document();
                lfc_226410.setName("lfc_226410.jpg");
                lfc_226410.setCreated(new Date());
                lfc_226410.setContentMd5("abbda56a02ca7d81fda888760d08127c");
                lfc_226410.setMimeType(jpegMimeType);
                jpegMimeType.addDocument(lfc_226410);
                lfc_226410.setDirectory(lfcPicturesDirectory);
                lfcPicturesDirectory.addDocument(lfc_226410);
                saveDocumentToRepository(lfc_226410);
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
                    comment.setUser(i % 2 == 0 ? jerryUser : johnnyUser);
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

        Directory johnnyRootDirectory = directoryDao.getRootDirectory(johnnyUser);

        Directory lfcDirectory = new Directory();
        lfcDirectory.setName("LFC");
        lfcDirectory.setParentDirectory(johnnyRootDirectory);
            Document liverpoolWallpaperDocument = new Document();
            liverpoolWallpaperDocument.setName("liverpool-wallpapers_1.jpg");
            liverpoolWallpaperDocument.setCreated(new Date());
            liverpoolWallpaperDocument.setContentMd5("81810819ba7a6e3718e0c87d5103eaf1");
            liverpoolWallpaperDocument.setMimeType(jpegMimeType);
            liverpoolWallpaperDocument.addTag(lfcTag);
            lfcTag.addDocument(liverpoolWallpaperDocument);
            saveDocumentToRepository(liverpoolWallpaperDocument);
            liverpoolWallpaperDocument.setDirectory(lfcDirectory);
            lfcDirectory.addDocument(liverpoolWallpaperDocument);
            liverpoolWallpaperDocument.setOwner(johnnyUser);

        ResourceGroup lfcFansRG = new ResourceGroup();
        lfcFansRG.setFounder(rootUser);
        lfcFansRG.setName("LFC fans");
        lfcFansRG.addDirectory(lfcPicturesDirectory);
        lfcFansRG.addDocument(liverpoolWallpaperDocument);
        resourceGroupDao.persist(lfcFansRG);

        mimeTypeDao.setSession(directoryDao.getSession());
        mimeTypeDao.persist(jpegMimeType);
        mimeTypeDao.persist(plainTextMimeType);
        mimeTypeDao.persist(docMimeType);
        mimeTypeDao.persist(pdfMimeType);
        directoryDao.persist(picturesDirectory);
        directoryDao.persist(documentsDirectory);
        directoryDao.persist(filesDirectory);
        directoryDao.persist(lfcDirectory);
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

        pdfMimeType = new MimeType();
        pdfMimeType.setDefaultExtension(".pdf");
        pdfMimeType.setType("application/pdf");

        docMimeType = new MimeType();
        docMimeType.setDefaultExtension(".doc");
        docMimeType.setType("application/x-tika-msoffice");

        plainTextMimeType = new MimeType();
        plainTextMimeType.setDefaultExtension(".txt");
        plainTextMimeType.setType("text/plain");

        mimeTypeDao.beginTransaction();
        mimeTypeDao.persist(jpegMimeType);
        mimeTypeDao.persist(pdfMimeType);
        mimeTypeDao.persist(docMimeType);
        mimeTypeDao.persist(plainTextMimeType);
        mimeTypeDao.commitTransaction();
    }

    private void initTags() {
        wallpaperTag = new Tag();
        wallpaperTag.setValue("tapety");

        lfcTag = new Tag();
        lfcTag.setValue("LFC");

        scholarTag = new Tag();
        scholarTag.setValue("szkolne");

        thesisTag = new Tag();
        thesisTag.setValue("Inżynierka");

        tagDao.beginTransaction();
        tagDao.persist(wallpaperTag);
        tagDao.persist(lfcTag);
        tagDao.persist(scholarTag);
        tagDao.persist(thesisTag);
        tagDao.commitTransaction();
    }

    private void saveDocumentToRepository(Document document) {
        InputStream documentIS = DatabaseInitializer.class.getClassLoader().getResourceAsStream(
                "documents/" + document.getName());
        try {
            byte[] gerrard20Bytes = ByteStreams.toByteArray(documentIS);
            fileManager.create(gerrard20Bytes, document.getFileSystemName());
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }
}
