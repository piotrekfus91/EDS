package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import com.google.common.collect.ImmutableList;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.FileSystemEntry;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
import pl.edu.pw.elka.pfus.eds.logic.validator.LogicValidator;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.LinkedList;
import java.util.List;

public class DirectoryFinderImpl implements DirectoryFinder {
    private Context context;
    private SecurityFacade securityFacade;
    private DirectoryDao directoryDao;
    private DocumentDao documentDao;

    public DirectoryFinderImpl(Context context, SecurityFacade securityFacade, DirectoryDao directoryDao,
                               DocumentDao documentDao) {
        this.context = context;
        this.securityFacade = securityFacade;
        this.directoryDao = directoryDao;
        this.documentDao = documentDao;
    }

    @Override
    public List<FileSystemEntry> getRootDirectoryAndSessionDocuments() {
        User currentUser = getCurrentUser();
        if(currentUser == null)
            return null;
        Directory rootDirectory = directoryDao.getRootDirectory(currentUser);
        List<Document> sessionDocuments = documentDao.getSessionDocuments(currentUser);
        List<FileSystemEntry> result = new LinkedList<>();
        result.add(rootDirectory);
        result.addAll(sessionDocuments);
        return result;
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
            return ImmutableList.copyOf(parentDirectory.getSubdirectories());
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

        LogicValidator.validateOwnershipOverDirectory(currentUser, directory);
        return directory;
    }

    private User getCurrentUser() {
        return securityFacade.getCurrentUser(context);
    }
}