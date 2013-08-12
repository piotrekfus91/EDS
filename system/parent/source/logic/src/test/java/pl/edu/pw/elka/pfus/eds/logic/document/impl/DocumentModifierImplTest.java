package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.AlreadyExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentModifierImplTest {
    private DocumentModifierImpl documentModifier;
    private DocumentDao documentDao;
    private DirectoryDao directoryDao;
    private UserDao userDao;
    private SecurityFacade securityFacade;
    private Context context;
    private User user;
    private Document document;
    private Document document2;
    private Directory directory;
    private Directory directory2;

    @BeforeMethod
    private void setUp() {
        user = new User();
        document = new Document();
        document2 = new Document();
        directory = new Directory();
        directory2 = new Directory();

        documentDao = getDocumentDao();
        directoryDao = getDirectoryDao();
        userDao = getUserDao();
        securityFacade = getSecurityFacade();
        context = getContext();
        documentModifier = new DocumentModifierImpl(documentDao, directoryDao, userDao, securityFacade, context);
    }

    @Test
    public void testRenamingForMoveToSameDir() throws Exception {
        when(documentDao.findById(anyInt())).thenReturn(document);
        document.setName("name");

        documentModifier.rename(1, "name");

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = AlreadyExistsException.class)
    public void testRenamingForSameFileInDirectory() throws Exception {
        when(documentDao.findById(anyInt())).thenReturn(document);
        document.setName("");
        document.setDirectory(directory);
        directory.addDocument(document2);
        document2.setName("name");

        documentModifier.rename(1, "name");
    }

    @Test
    public void testRenamingSuccess() throws Exception {
        when(documentDao.findById(anyInt())).thenReturn(document);
        document.setName("");
        document.setDirectory(directory);

        documentModifier.rename(1, "name");
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testMovingForUserNotOwnerOfDocument() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        document.setDirectory(directory);
        directory.setId(2);

        documentModifier.move(1, 1);
    }

    @Test
    public void testMovingToSameDir() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        document.setDirectory(directory);
        directory.setId(1);

        documentModifier.move(1, 1);
    }

    @Test
    public void testMovingSuccess() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        document.setDirectory(directory);
        document.setOwner(user);
        directory.setOwner(user);
        directory.setId(2); // inne niz parametr wywolania
        directory2.setOwner(user);

        documentModifier.move(1, 1);

        assertThat(document.getDirectory()).isEqualTo(directory2);
    }

    @Test
    public void testDeleting() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        document.setOwner(user);

        documentModifier.delete(1);

        assertThat(true).isTrue();
    }

    @Test
    public void testIsMoveToSameDirectoryExpectedTrue() throws Exception {
        directory.setId(1);

        assertThat(documentModifier.isMoveToSameDirectory(1, directory)).isTrue();
    }

    @Test
    public void testIsMoveToSameDirectoryExpectedFalse() throws Exception {
        directory.setId(1);

        assertThat(documentModifier.isMoveToSameDirectory(2, directory)).isFalse();
    }

    @Test
    public void testValidateOwnershipOverDocumentNoEx() throws Exception {
        document.setOwner(user);

        documentModifier.validateOwnershipOverDocument(user, document);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testValidateOwnershipOverDocumentEx() throws Exception {
        documentModifier.validateOwnershipOverDocument(user, document);
    }

    @Test
    public void testValidateOwnershipOverDirectoryNoEx() throws Exception {
        directory.setOwner(user);

        documentModifier.validateOwnershipOverDirectory(user, directory);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testValidateOwnershipOverDirectoryEx() throws Exception {
        documentModifier.validateOwnershipOverDirectory(user, directory);
    }

    @Test
    public void testValidateExistenceNoEx() throws Exception {
        documentModifier.validateExistence(document);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testValidateExistenceEx() throws Exception {
        documentModifier.validateExistence(null);
    }

    @Test
    public void testIsFileWithNameInDirectoryForNoSuchFile() throws Exception {
        document.setName("name1");
        document2.setName("name2");
        directory.addDocument(document);
        directory.addDocument(document2);

        assertThat(documentModifier.isFileWithNameInDirectory(directory, "name3")).isFalse();
    }

    @Test
    public void testIsFileWithNameInDirectoryForFile() throws Exception {
        document.setName("name");
        directory.addDocument(document);

        assertThat(documentModifier.isFileWithNameInDirectory(directory, "name")).isTrue();
    }

    private Context getContext() {
        return mock(Context.class);
    }

    private SecurityFacade getSecurityFacade() {
        return mock(SecurityFacade.class);
    }

    private UserDao getUserDao() {
        return mock(UserDao.class);
    }

    private DirectoryDao getDirectoryDao() {
        return mock(DirectoryDao.class);
    }

    private DocumentDao getDocumentDao() {
        return mock(DocumentDao.class);
    }
}
