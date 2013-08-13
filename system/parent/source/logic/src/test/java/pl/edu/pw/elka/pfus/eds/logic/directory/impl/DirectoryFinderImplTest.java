package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryFinder;
import pl.edu.pw.elka.pfus.eds.logic.directory.impl.DirectoryFinderImpl;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.LinkedList;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DirectoryFinderImplTest {
    private DirectoryFinder finder;
    private Context context;
    private SecurityFacade securityFacade;
    private DirectoryDao directoryDao;

    @BeforeMethod
    private void beforeMethod() {
        context = getMockContext();
        securityFacade = getMockSecurityFacade();
        directoryDao = getMockDirectoryDao();
        finder = new DirectoryFinderImpl(context, securityFacade, directoryDao);
    }

    @Test
    public void testGetRootForNotLoggedUser() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(null);
        assertThat(finder.getRootDirectory()).isNull();
    }

    @Test
    public void testGetRootForLoggedUser() throws Exception {
        Directory expectedDir = getFreeLevelStructure();
        User user = getMockUser();
        when(securityFacade.isLogged(context)).thenReturn(true);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(directoryDao.getRootDirectory(user)).thenReturn(getFreeLevelStructure());

        assertThat(finder.getRootDirectory()).isEqualTo(expectedDir);
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testGetSubdirectoriesForNotLoggedUser() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(null);
        Directory parent = getFreeLevelStructure();
        parent.setId(1);
        finder.getSubdirectories(parent).isEmpty();
    }

    @Test
    public void testGetSubdirectoriesForLoggedUser() throws Exception {
        User user = getMockUser();
        when(securityFacade.isLogged(context)).thenReturn(true);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory parent = getFreeLevelStructure();
        parent.setOwner(user);
        when(user.isOwnerOfDirectory(parent)).thenReturn(true);
        when(directoryDao.getDirectoryWithSubdirectoriesAndOwner(1)).thenReturn(parent);

        assertThat(finder.getSubdirectories(1)).isEqualTo(new LinkedList<>(parent.getSubdirectories()));
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testGetByIdForNotOwner() throws Exception {
        User user = getMockUser();
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory directory = new Directory();
        directory.setOwner(new User());
        when(directoryDao.findById(anyInt())).thenReturn(directory);

        finder.getById(1);
    }

    @Test
    public void testGetByIdForOwner() throws Exception {
        User user = new User();
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory directory = new Directory();
        directory.setOwner(user);
        when(directoryDao.findById(anyInt())).thenReturn(directory);

        assertThat(finder.getById(1)).isEqualTo(directory);
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testFileSystemEntriesIfParentDirNotFound() throws Exception {
        when(directoryDao.getDirectoryWithFileSystemEntriesAndOwner(anyInt())).thenReturn(null);

        finder.getFileSystemEntries(1);
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testFileSystemEntriesForNotOwner() throws Exception {
        Directory parentDirectory = getFreeLevelStructure();
        when(directoryDao.getDirectoryWithFileSystemEntriesAndOwner(anyInt())).thenReturn(parentDirectory);
        User user = new User();
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        finder.getFileSystemEntries(1);
    }

    @Test
    public void testFileSystemEntriesForSuccess() throws Exception {
        Directory parentDirectory = getFreeLevelStructure();
        when(directoryDao.getDirectoryWithFileSystemEntriesAndOwner(anyInt())).thenReturn(parentDirectory);
        User user = new User();
        parentDirectory.setOwner(user);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        assertThat(finder.getFileSystemEntries(1)).isEqualTo(parentDirectory.getFileSystemEntries());
    }

    private DirectoryDao getMockDirectoryDao() {
        DirectoryDao directoryDao = mock(DirectoryDao.class);
        when(directoryDao.getRootDirectory(1)).thenReturn(getFreeLevelStructure());
        return directoryDao;
    }

    private SecurityFacade getMockSecurityFacade() {
        return mock(SecurityFacade.class);
    }

    private Context getMockContext() {
        return mock(Context.class);
    }

    private User getMockUser() {
        User user = mock(User.class);
        when(user.getId()).thenReturn(1);
        return user;
    }

    private Directory getFreeLevelStructure() {
        Directory parentDir = new Directory();
        parentDir.setName("parent");
        Directory firstSubdir = new Directory();
        firstSubdir.setName("firstSubdir");
        firstSubdir.setParentDirectory(parentDir);
        Directory secondSubdir = new Directory();
        secondSubdir.setName("secondSubdir");
        secondSubdir.setParentDirectory(parentDir);
        Directory subsubdir = new Directory();
        subsubdir.setName("subsubdir");
        subsubdir.setParentDirectory(firstSubdir);
        return parentDir;
    }
}
