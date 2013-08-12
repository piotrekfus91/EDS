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
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
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

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testMovingForUserNotOwnerOfDocument() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        when(directoryDao.findById(anyInt())).thenReturn(directory);
        document.setDirectory(directory);

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
        directory2.setOwner(user);

        documentModifier.move(1, 1);

        assertThat(document.getDirectory()).isEqualTo(directory2);
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
