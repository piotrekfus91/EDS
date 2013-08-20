package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.AlreadyExistsException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidMimeTypeException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.mime.type.detector.MimeTypeDetector;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.file.system.FileManager;
import pl.edu.pw.elka.pfus.eds.util.hash.ByteArrayHasher;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class DocumentModifierImplTest {
    private DocumentModifierImpl documentModifier;
    private DocumentDao documentDao;
    private DirectoryDao directoryDao;
    private MimeTypeDao mimeTypeDao;
    private UserDao userDao;
    private MimeTypeDetector mimeTypeDetector;
    private SecurityFacade securityFacade;
    private Context context;
    private ByteArrayHasher hasher;
    private FileManager fileManager;
    private User user;
    private MimeType mimeType;
    private Document document;
    private Document document2;
    private Directory directory;
    private Directory directory2;

    @BeforeMethod
    private void setUp() {
        user = new User();
        mimeType = new MimeType();
        document = new Document();
        document2 = new Document();
        directory = new Directory();
        directory2 = new Directory();

        mimeTypeDetector = mock(MimeTypeDetector.class);

        mimeTypeDao = getMimeTypeDao();
        documentDao = getDocumentDao();
        directoryDao = getDirectoryDao();
        userDao = getUserDao();
        securityFacade = getSecurityFacade();
        hasher = getHasher();
        fileManager = getFileManager();
        context = getContext();
        documentModifier = new DocumentModifierImpl(documentDao, mimeTypeDao, directoryDao, userDao, securityFacade,
                fileManager, mimeTypeDetector, hasher, context);
    }

    @Test(expectedExceptions = InvalidMimeTypeException.class)
    public void testCreatingForWrongMimeType() throws Exception {
        byte[] input = new byte[0];
        mimeType.setEnabled(false);
        when(mimeTypeDetector.detect(input)).thenReturn(mimeType);

        documentModifier.create("", input);
    }

    @Test
    public void testCreatingForSuccess() throws Exception {
        byte[] input = new byte[0];
        when(mimeTypeDetector.detect(input)).thenReturn(mimeType);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(userDao.findById(anyInt())).thenReturn(user);
        user.setId(1);
        when(hasher.getString(input)).thenReturn("");

        documentModifier.create("", input);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InternalException.class)
    public void testCreatingForRollback() throws Exception {
        byte[] input = new byte[0];
        when(mimeTypeDetector.detect(input)).thenReturn(mimeType);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(userDao.findById(anyInt())).thenReturn(user);
        user.setId(1);
        when(hasher.getString(input)).thenReturn("");
        doThrow(new InternalException()).when(documentDao).commitTransaction();

        documentModifier.create("", input);
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

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InternalException.class)
    public void testRenamingForRollback() throws Exception {
        when(documentDao.findById(anyInt())).thenReturn(document);
        doThrow(new InternalException()).when(documentDao).commitTransaction();
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
        document.setOwner(user);

        documentModifier.move(1, 1);

        assertThat(true).isTrue();
    }

    @Test
    public void testMovingSuccess() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        when(directoryDao.merge(directory)).thenReturn(directory);
        when(directoryDao.merge(directory2)).thenReturn(directory2);
        document.setDirectory(directory);
        document.setOwner(user);
        directory.setOwner(user);
        directory.setId(2); // inne niz parametr wywolania
        directory2.setOwner(user);

        documentModifier.move(1, 1);

        assertThat(document.getDirectory()).isEqualTo(directory2);
    }

    @Test(expectedExceptions = InternalException.class)
    public void testMovingForRollback() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        doThrow(new InternalException()).when(directoryDao).commitTransaction();
        document.setDirectory(directory);
        document.setOwner(user);
        directory.setOwner(user);
        directory.setId(2); // inne niz parametr wywolania
        directory2.setOwner(user);

        documentModifier.move(1, 1);

        document.getDirectory();
    }

    @Test
    public void testDeleting() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        document.setCreated(new Date());
        document.setOwner(user);

        documentModifier.delete(1);

        assertThat(true).isTrue();
    }

    @Test(expectedExceptions = InternalException.class)
    public void testDeletingForRollback() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        doThrow(new InternalException()).when(documentDao).commitTransaction();
        document.setOwner(user);

        documentModifier.delete(1);
    }

    private Context getContext() {
        return mock(Context.class);
    }

    private SecurityFacade getSecurityFacade() {
        return mock(SecurityFacade.class);
    }

    private MimeTypeDao getMimeTypeDao() {
        return mock(MimeTypeDao.class);
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

    private FileManager getFileManager() {
        return mock(FileManager.class);
    }

    private ByteArrayHasher getHasher() {
        return mock(ByteArrayHasher.class);
    }
}
