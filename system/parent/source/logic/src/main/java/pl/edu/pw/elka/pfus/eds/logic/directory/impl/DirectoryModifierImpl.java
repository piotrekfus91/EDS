package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryModifier;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

public class DirectoryModifierImpl implements DirectoryModifier {
    private static final Logger logger = Logger.getLogger(DirectoryModifierImpl.class);
    private DirectoryDao directoryDao;
    private SecurityFacade securityFacade;
    private Context context;

    public DirectoryModifierImpl(DirectoryDao directoryDao, SecurityFacade securityFacade, Context context) {
        this.directoryDao = directoryDao;
        this.securityFacade = securityFacade;
        this.context = context;
    }

    @Override
    public Directory delete(Directory directory) {
        return delete(directory.getId());
    }

    @Override
    public Directory delete(int id) {
        try {
            directoryDao.beginTransaction();
            Directory directory = directoryDao.findById(id);
            User currentUser = securityFacade.getCurrentUser(context);

            if(directory == null || !currentUser.isOwnerOfDirectory(directory)) {
                logger.warn("directory with id " + id + " not found or is not owned by user " + currentUser);
                return null;
            }

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
            logger.error(e.getMessage(), e);
            directoryDao.rollbackTransaction();
            return null;
        }
    }

    @Override
    public Directory rename(int id, String newName) {
        try {
            directoryDao.beginTransaction();
            Directory directory = directoryDao.findById(id);
            directory.setName(newName);
            directoryDao.persist(directory);
            directoryDao.commitTransaction();
            return directory;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            directoryDao.rollbackTransaction();
            return null;
        }
    }
}
