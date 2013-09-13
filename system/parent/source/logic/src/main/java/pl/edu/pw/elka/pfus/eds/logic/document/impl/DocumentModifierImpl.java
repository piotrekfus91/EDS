package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentModifier;
import pl.edu.pw.elka.pfus.eds.logic.error.handler.ErrorHandler;
import pl.edu.pw.elka.pfus.eds.logic.exception.AlreadyExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.mime.type.detector.MimeTypeDetector;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;
import pl.edu.pw.elka.pfus.eds.util.hash.ByteArrayHasher;

import java.util.Date;

public class DocumentModifierImpl implements DocumentModifier {
    private static final Logger logger = Logger.getLogger(DocumentModifierImpl.class);
    private DocumentDao documentDao;
    private MimeTypeDao mimeTypeDao;
    private DirectoryDao directoryDao;
    private UserDao userDao;
    private SecurityFacade securityFacade;
    private FileManager fileManager;
    private MimeTypeDetector mimeTypeDetector;
    private ByteArrayHasher hasher;
    private Context context;

    public DocumentModifierImpl(DocumentDao documentDao, MimeTypeDao mimeTypeDao, DirectoryDao directoryDao,
                                UserDao userDao, SecurityFacade securityFacade, FileManager fileManager,
                                MimeTypeDetector mimeTypeDetector, ByteArrayHasher hasher, Context context) {
        this.documentDao = documentDao;
        this.mimeTypeDao = mimeTypeDao;
        this.directoryDao = directoryDao;
        this.userDao = userDao;
        this.securityFacade = securityFacade;
        this.fileManager = fileManager;
        this.mimeTypeDetector = mimeTypeDetector;
        this.hasher = hasher;
        this.context = context;
    }

    @Override
    public void create(String name, byte[] input) {
        mimeTypeDao.setSession(documentDao.getSession());
        userDao.setSession(documentDao.getSession());
        mimeTypeDetector.setSession(documentDao.getSession());

        MimeType mimeType = mimeTypeDetector.detect(input);
        LogicValidator.validateMimeTypeEnabled(mimeType);

        User owner = userDao.findById(securityFacade.getCurrentUser(context).getId());

        Document document = new Document();
        document.setName(name);
        document.setCreated(new Date());
        document.setOwner(owner);
        document.setContentMd5(hasher.getString(input));


        mimeType.addDocument(document);
        document.setMimeType(mimeType);

        try {
            documentDao.beginTransaction();
            documentDao.persist(document);
            mimeTypeDao.persist(mimeType);
            fileManager.create(input, document.getFileSystemName());
            documentDao.commitTransaction();
        } catch (Exception e) {
            documentDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            fileManager.delete(document.getFileSystemName(), document.getContentMd5());
            throw new InternalException();
        }
    }

    @Override
    public void rename(int documentId, String newName) {
        Document document = documentDao.findById(documentId);
        if(document.getName().equals(newName))
            return;

        Directory directory = document.getDirectory();
        if(LogicValidator.isFileWithNameInDirectory(directory, newName))
            throw new AlreadyExistsException();

        try {
            documentDao.beginTransaction();
            document.setName(newName);
            documentDao.persist(document);
            documentDao.commitTransaction();
        } catch (Exception e) {
            ErrorHandler.handle(e, documentDao);
        }
    }

    @Override
    public void move(int documentId, int destinationDirectoryId) {
        directoryDao.setSession(documentDao.getSession());
        userDao.setSession(documentDao.getSession());

        User currentUser = securityFacade.getCurrentUser(context);
        Document document = documentDao.findById(documentId);
        Directory sourceDirectory = document.getDirectory();

        LogicValidator.validateOwnershipOverDocument(currentUser, document);
        if(!document.isSessionDocument()) {
            if (LogicValidator.isMoveToSameDirectory(destinationDirectoryId, sourceDirectory)) {
                return;
            }
            LogicValidator.validateOwnershipOverDirectory(currentUser, sourceDirectory);
        }


        Directory destinationDirectory = directoryDao.findById(destinationDirectoryId);
        LogicValidator.validateOwnershipOverDirectory(currentUser, destinationDirectory);

        if(LogicValidator.isFileWithNameInDirectory(destinationDirectory, document.getName()))
            throw new AlreadyExistsException();

        try {
            directoryDao.beginTransaction();
            destinationDirectory.addDocument(document);
            document.setDirectory(destinationDirectory);
            destinationDirectory = directoryDao.merge(destinationDirectory);
            if(sourceDirectory != null) {
                sourceDirectory.removeDocument(document);
                sourceDirectory = directoryDao.merge(sourceDirectory);
                directoryDao.persist(sourceDirectory);
            }
            directoryDao.persist(destinationDirectory);
            directoryDao.commitTransaction();
        } catch(Exception e) {
            directoryDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    @Override
    public void delete(int documentId) {
        Document document = documentDao.findById(documentId);
        LogicValidator.validateExistence(document);

        User currentUser = securityFacade.getCurrentUser(context);
        LogicValidator.validateOwnershipOverDocument(currentUser, document);

        try {
            documentDao.beginTransaction();
            documentDao.delete(document);
            fileManager.delete(document.getFileSystemName(), document.getContentMd5());
            documentDao.commitTransaction();
        } catch (Exception e) {
            documentDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    @Override
    public void cleanSessionDocuments() {
        User currentUser = securityFacade.getCurrentUser(context);

        try {
            documentDao.beginTransaction();
            documentDao.deleteSessionDocuments(currentUser);
            documentDao.commitTransaction();
        } catch (Exception e) {
            documentDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }
}
