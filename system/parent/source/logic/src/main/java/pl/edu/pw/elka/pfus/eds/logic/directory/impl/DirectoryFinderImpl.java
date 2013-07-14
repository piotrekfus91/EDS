package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

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
        User currentUser = securityFacade.getCurrentUser(context);
        return directoryDao.getRootDirectories(currentUser);
    }
}