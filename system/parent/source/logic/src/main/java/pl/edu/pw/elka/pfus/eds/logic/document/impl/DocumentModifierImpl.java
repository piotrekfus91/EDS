package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentModifier;
import pl.edu.pw.elka.pfus.eds.logic.exception.AlreadyExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

public class DocumentModifierImpl implements DocumentModifier {
    private static final Logger logger = Logger.getLogger(DocumentModifierImpl.class);
    private DocumentDao documentDao;
    private DirectoryDao directoryDao;
    private UserDao userDao;
    private SecurityFacade securityFacade;
    private Context context;

    public DocumentModifierImpl(DocumentDao documentDao, DirectoryDao directoryDao,
                                UserDao userDao, SecurityFacade securityFacade, Context context) {
        this.documentDao = documentDao;
        this.directoryDao = directoryDao;
        this.userDao = userDao;
        this.securityFacade = securityFacade;
        this.context = context;
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
            documentDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    @Override
    public void move(int documentId, int destinationDirectoryId) {
        directoryDao.setSession(documentDao.getSession());
        userDao.setSession(documentDao.getSession());

        User currentUser = securityFacade.getCurrentUser(context);
        Document document = documentDao.findById(documentId);
        Directory sourceDirectory = document.getDirectory();

        if(LogicValidator.isMoveToSameDirectory(destinationDirectoryId, sourceDirectory))
            return;

        LogicValidator.validateOwnershipOverDirectory(currentUser, sourceDirectory);
        LogicValidator.validateOwnershipOverDocument(currentUser, document);

        Directory destinationDirectory = directoryDao.findById(destinationDirectoryId);
        LogicValidator.validateOwnershipOverDirectory(currentUser, destinationDirectory);

        if(LogicValidator.isFileWithNameInDirectory(destinationDirectory, document.getName()))
            throw new AlreadyExistsException();

        try {
            directoryDao.beginTransaction();
            sourceDirectory.removeDocument(document);
            destinationDirectory.addDocument(document);
            document.setDirectory(destinationDirectory);
            sourceDirectory = directoryDao.merge(sourceDirectory);
            destinationDirectory = directoryDao.merge(destinationDirectory);
            directoryDao.persist(sourceDirectory);
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
            documentDao.commitTransaction();
        } catch (Exception e) {
            documentDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }
}
