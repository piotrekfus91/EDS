package pl.edu.pw.elka.pfus.eds.logic.directory.impl;

import com.google.common.collect.ImmutableList;
import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.directory.DirectoryModifier;
import pl.edu.pw.elka.pfus.eds.logic.exception.*;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class DirectoryModifierImplTest {
    private DirectoryModifier modifier;
    private Directory directory;
    private DocumentDao documentDao;
    private FileManager fileManager;
    private User user;
    private Context context;
    private SecurityFacade securityFacade;
    private UserDao userDao;
    private DirectoryDao directoryDao;

    @BeforeMethod
    private void setUp() {
        directory = new Directory();
        user = new User();
        context = mock(Context.class);
        securityFacade = mock(SecurityFacade.class);
        userDao = mock(UserDao.class);
        documentDao = mock(DocumentDao.class);
        directoryDao = mock(DirectoryDao.class);
        fileManager = mock(FileManager.class);

        modifier = new DirectoryModifierImpl(directoryDao, documentDao, fileManager, userDao, securityFacade, context);
    }

    @Test(expectedExceptions = AlreadyExistsException.class)
    public void testAddingForDuplicateNames() throws Exception {
        Directory directory2 = new Directory();
        directory2.setName("name");
        when(directoryDao.getSubdirectories(anyInt())).thenReturn(ImmutableList.of(directory2));

        modifier.add(1, "name");
    }

    @Test
    public void testAddingForSuccess() throws Exception {
        List<Directory> siblingList = new LinkedList<>();
        when(directoryDao.getSubdirectories(anyInt())).thenReturn(siblingList);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        Directory directory = modifier.add(1, "name");
        assertThat(directory.getName()).isEqualTo("name");
    }

    @Test(expectedExceptions = InternalException.class)
    public void testAddingForRollback() throws Exception {
        List<Directory> siblingList = new LinkedList<>();
        when(directoryDao.getSubdirectories(anyInt())).thenReturn(siblingList);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        doThrow(new InternalException()).when(directoryDao).commitTransaction();

        modifier.add(1, "name");
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testDeletingForNotFoundObject() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(null);

        modifier.delete(1);
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testDeletingForNotOwner() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        modifier.delete(1);
    }

    @Test(expectedExceptions = LogicException.class)
    public void testDeletingRoot() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        directory.setOwner(user);

        modifier.delete(1);
    }

    @Test
    public void testDeletingNotRoot() throws Exception {
        when(directoryDao.findById(1)).thenReturn(directory);
        directory.setId(1);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory parent = new Directory();
        directory.setParentDirectory(parent);
        parent.setId(2);
        when(directoryDao.findById(2)).thenReturn(parent);
        directory.setOwner(user);

        assertThat(modifier.delete(directory)).isEqualTo(parent);
    }

    @Test(expectedExceptions = InternalException.class)
    public void testDeletingForRollback() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        directory.setParentDirectory(new Directory());
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        doThrow(new InternalException()).when(directoryDao).commitTransaction();
        directory.setOwner(user);

        modifier.delete(1);
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testMovingForNotOwner() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        modifier.move(1, 1);
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testMovingForNotOwningDestinationDirectory() throws Exception {
        when(directoryDao.findById(1)).thenReturn(directory);
        Directory directory2 = new Directory();
        when(directoryDao.findById(2)).thenReturn(directory2);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        directory.setOwner(user);

        modifier.move(1, 2);
    }

    @Test
    public void testMovingForSuccess() throws Exception {
        when(directoryDao.findById(1)).thenReturn(directory);
        Directory directory2 = new Directory();
        when(directoryDao.findById(2)).thenReturn(directory2);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory parent = new Directory();
        directory.setParentDirectory(parent);
        directory.setOwner(user);
        directory2.setOwner(user);

        modifier.move(1, 2);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InternalException.class)
    public void testMovingForRollback() throws Exception {
        when(directoryDao.findById(1)).thenReturn(directory);
        Directory directory2 = new Directory();
        when(directoryDao.findById(2)).thenReturn(directory2);
        doThrow(new InternalException()).when(directoryDao).commitTransaction();
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        Directory parent = new Directory();
        directory.setParentDirectory(parent);
        directory.setOwner(user);
        directory2.setOwner(user);

        modifier.move(1, 2);
    }

    @Test(expectedExceptions = LogicException.class)
    public void testRenamingRoot() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);

        modifier.rename(1, "");
    }

    @Test
    public void testRenamingToSameName() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        Directory parent = new Directory();
        directory.setName("name");
        directory.setParentDirectory(parent);

        assertThat(modifier.rename(1, "name")).isEqualTo(directory);
    }

    @Test
    public void testRenamingForSuccess() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        directory.setName("sdkjflsdkf");
        Directory parent = new Directory();
        directory.setParentDirectory(parent);

        assertThat(modifier.rename(1, "name")).isEqualTo(directory);
        assertThat(directory.getName()).isEqualTo("name");
    }

    @Test(expectedExceptions = InternalException.class)
    public void testRenamingForRollback() throws Exception {
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        doThrow(new InternalException()).when(directoryDao).commitTransaction();
        directory.setName("sdkjflsdkf");
        Directory parent = new Directory();
        directory.setParentDirectory(parent);

        modifier.rename(1, "name");
    }
}
