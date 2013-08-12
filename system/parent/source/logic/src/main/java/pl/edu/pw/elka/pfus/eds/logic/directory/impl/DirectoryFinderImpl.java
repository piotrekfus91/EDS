package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.LinkedList;
import java.util.List;

public class DirectoryFinderImpl implements DirectoryFinder {
    private Context context;
    private SecurityFacade securityFacade;
    private DirectoryDao directoryDao;

    public DirectoryFinderImpl(Context context, SecurityFacade securityFacade, DirectoryDao directoryDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.directoryDao = directoryDao;
    }

    @Override
    public Directory getRootDirectory() {
        User currentUser = getCurrentUser();
        return directoryDao.getRootDirectory(currentUser);
    }

    @Override
    public List<Directory> getSubdirectories(Directory directory) {
        return getSubdirectories(directory.getId());
    }

    @Override
    public List<Directory> getSubdirectories(int directoryId) {
        User currentUser = getCurrentUser();
        Directory parentDirectory = directoryDao.getDirectoryWithSubdirectoriesAndOwner(directoryId);
        if(parentDirectory == null)
            throw new ObjectNotFoundException();

        if(currentUser.isOwnerOfDirectory(parentDirectory)) {
            return new LinkedList<>(parentDirectory.getSubdirectories());
        } else {
            throw new InvalidPrivilegesException();
        }
    }

    @Override
    public List<FileSystemEntry> getFileSystemEntries(int directoryId) {
        User currentUser = getCurrentUser();
        Directory parentDirectory = directoryDao.getDirectoryWithFileSystemEntriesAndOwner(directoryId);
        if(parentDirectory == null)
            throw new ObjectNotFoundException();

        if(currentUser.isOwnerOfDirectory(parentDirectory)) {
            return parentDirectory.getFileSystemEntries();
        } else {
            throw new InvalidPrivilegesException();
        }
    }

    @Override
    public Directory getById(int id) {
        User currentUser = getCurrentUser();
        Directory directory = directoryDao.findById(id);
        if(currentUser.isOwnerOfDirectory(directory))
            return directory;
        else
            throw new InvalidPrivilegesException();
    }

    private User getCurrentUser() {
        return securityFacade.getCurrentUser(context);
    }
}