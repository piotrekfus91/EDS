package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryModifier;
import pl.edu.pw.elka.pfus.eds.logic.document.DocumentModifier;
import pl.edu.pw.elka.pfus.eds.logic.exception.AlreadyExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.exception.LogicException;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.List;

public class DirectoryModifierImpl implements DirectoryModifier {
    private static final Logger logger = Logger.getLogger(DirectoryModifierImpl.class);
    private DirectoryDao directoryDao;
    private DocumentModifier documentModifier;
    private UserDao userDao;
    private SecurityFacade securityFacade;
    private Context context;

    public DirectoryModifierImpl(DirectoryDao directoryDao, DocumentModifier documentModifier, UserDao userDao,
                                 SecurityFacade securityFacade, Context context) {
        this.directoryDao = directoryDao;
        this.documentModifier = documentModifier;
        this.userDao = userDao;
        this.securityFacade = securityFacade;
        this.context = context;
    }

    @Override
    public Directory add(int parentDirectoryId, String name) {
        logger.info("adding subdirectory for directory with id " + parentDirectoryId + ", name is: " + name);
        List<Directory> siblings = directoryDao.getSubdirectories(parentDirectoryId);
        if(isAnyWithSameName(name, siblings)) {
            throw new AlreadyExistsException();
        }

        Directory directory = new Directory();
        directory.setName(name);
        User owner = securityFacade.getCurrentUser(context);
        owner = userDao.findById(owner.getId());
        directory.setOwner(owner);
        directory.setParentDirectory(directoryDao.findById(parentDirectoryId));

        try {
            directoryDao.beginTransaction();
            directoryDao.persist(directory);
            directoryDao.commitTransaction();
            return directory;
        } catch (Exception e) {
            directoryDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    @Override
    public Directory delete(Directory directory) {
        return delete(directory.getId());
    }

    @Override
    public Directory delete(int id) {
        Directory directory = directoryDao.findById(id);
        User currentUser = securityFacade.getCurrentUser(context);

        LogicValidator.validateExistence(directory);
        LogicValidator.validateOwnershipOverDirectory(currentUser, directory);

        if(directory.isRootDirectory())
            throw new LogicException("Nie można usunąć katalogu głównego");

        try {
            directoryDao.beginTransaction();
            int parentDirectoryId = directory.getParentDirectory().getId();
            Directory parentDirectory = directoryDao.findById(parentDirectoryId);
            recursiveRemoveDirectory(directory);
            parentDirectory.removeSubdirectory(directory);
            directoryDao.delete(directory);
            directoryDao.commitTransaction();
            logger.info("removed directory with id " + id + " and name " + directory.getName());
            return parentDirectory;
        } catch (Exception e) {
            directoryDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    private void recursiveRemoveDirectory(Directory directory) {
        Directory dir = directoryDao.findById(directory.getId());
        for(Directory subdir : directory.getSubdirectories()) {
            recursiveRemoveDirectory(subdir);
        }
        for(Document document : directory.getDocuments()) {
            documentModifier.delete(document.getId());
        }
        directoryDao.delete(dir);
    }

    @Override
    public void move(int directoryId, int destinationDirectoryId) {
        User currentUser = securityFacade.getCurrentUser(context);
        Directory directory = directoryDao.findById(directoryId);
        LogicValidator.validateOwnershipOverDirectory(currentUser, directory);

        Directory destinationDirectory = directoryDao.findById(destinationDirectoryId);
        LogicValidator.validateOwnershipOverDirectory(currentUser, destinationDirectory);

        Directory sourceDirectory = directory.getParentDirectory();

        try {
            directoryDao.beginTransaction();
            sourceDirectory.removeSubdirectory(directory);
            destinationDirectory.addSubdirectory(directory);
            directory.setParentDirectory(destinationDirectory);
            directoryDao.persist(sourceDirectory);
            directoryDao.persist(destinationDirectory);
            directoryDao.commitTransaction();
        } catch (Exception e) {
            directoryDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    @Override
    public Directory rename(int id, String newName) {
        Directory directory = directoryDao.findById(id);
        if(directory.isRootDirectory())
            throw new LogicException("Nie można zmienić nazwy katalogu głównego");

        if(directory.getName().equals(newName))
            return directory;

        if(hasAnySiblingThisName(directory, newName))
            throw new AlreadyExistsException();

        directory.setName(newName);
        try {
            directoryDao.beginTransaction();
            directoryDao.persist(directory);
            directoryDao.commitTransaction();
            return directory;
        } catch (Exception e) {
            directoryDao.rollbackTransaction();
            logger.error(e.getMessage(), e);
            throw new InternalException();
        }
    }

    private boolean hasAnySiblingThisName(Directory directory, String name) {
        if(directory.isRootDirectory())
            return false;
        List<Directory> siblings = directoryDao.getSubdirectories(directory.getParentDirectory());
        return isAnyWithSameName(name, siblings);
    }

    private boolean isAnyWithSameName(String name, List<Directory> siblings) {
        for(Directory sibling : siblings) {
            if(name.equals(sibling.getName()))
                return true;
        }
        return false;
    }
}
