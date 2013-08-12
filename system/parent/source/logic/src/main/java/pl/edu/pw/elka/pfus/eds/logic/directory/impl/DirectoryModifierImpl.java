package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryModifier;
import pl.edu.pw.elka.pfus.eds.logic.exception.*;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.List;

public class DirectoryModifierImpl implements DirectoryModifier {
    private static final Logger logger = Logger.getLogger(DirectoryModifierImpl.class);
    private DirectoryDao directoryDao;
    private UserDao userDao;
    private SecurityFacade securityFacade;
    private Context context;

    public DirectoryModifierImpl(DirectoryDao directoryDao, UserDao userDao, SecurityFacade securityFacade,
                                 Context context) {
        this.directoryDao = directoryDao;
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

        if(directory == null)
            throw new ObjectNotFoundException();

        if(!currentUser.isOwnerOfDirectory(directory))
            throw new InvalidPrivilegesException();

        try {
            directoryDao.beginTransaction();
            Directory toReturn;
            if(directory.isRootDirectory()) {
                directoryDao.delete(directory);
                toReturn = null;
            } else {
                int parentDirectoryId = directory.getParentDirectory().getId();
                toReturn = directoryDao.findById(parentDirectoryId);
                toReturn.removeSubdirectory(directory);
                directoryDao.delete(directory);
            }
            logger.info("removed directory with id " + id + " and name " + directory.getName());
            directoryDao.commitTransaction();
            return toReturn;
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
