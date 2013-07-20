package pl.edu.pw.elka.pfus.eds.logic.directory;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.impl.DirectoryFinderImpl;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class DirectoryFinderTest {
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
        assertThat(finder.getRootDirectories()).isEmpty();
    }

    @Test
    public void testGetRootForLoggedUser() throws Exception {
        List<Directory> expectedDirs = getSampleDirectories();
        User user = getMockUser();
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(directoryDao.getRootDirectories(user)).thenReturn(expectedDirs);

        assertThat(finder.getRootDirectories()).isEqualTo(expectedDirs);
    }

    @Test
    public void testGetSubdirectoriesForNotLoggedUser() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(null);
        Directory parent = getFreeLevelStructure();
        parent.setId(1);
        assertThat(finder.getSubdirectories(parent)).isEmpty();
    }

    @Test
    public void testGetSubdirectoriesForLoggedUser() throws Exception {
        User user = getMockUser();
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory parent = getFreeLevelStructure();
        parent.setOwner(user);
        when(directoryDao.getDirectoryWithSubdirectoriesAndOwner(1)).thenReturn(parent);

        assertThat(finder.getSubdirectories(1)).isEqualTo(new LinkedList<>(parent.getSubdirectories()));
    }

    private DirectoryDao getMockDirectoryDao() {
        DirectoryDao directoryDao = mock(DirectoryDao.class);
        when(directoryDao.getRootDirectories(1)).thenReturn(getSampleDirectories());
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

    private Directory getMockDirectory() {
        return mock(Directory.class);
    }

    private List<Directory> getSampleDirectories() {
        return new LinkedList(getFreeLevelStructure().getSubdirectories());
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
