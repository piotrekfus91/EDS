package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import com.google.common.collect.ImmutableList;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
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
    public List<Directory> getRootDirectories() {
        User currentUser = getCurrentUser();
        return directoryDao.getRootDirectories(currentUser);
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
            return ImmutableList.of();

        if(parentDirectory.getOwner().equals(currentUser)) {
            return new LinkedList<>(parentDirectory.getSubdirectories());
        } else {
            return ImmutableList.of();
        }
    }

    private User getCurrentUser() {
        return securityFacade.getCurrentUser(context);
    }
}