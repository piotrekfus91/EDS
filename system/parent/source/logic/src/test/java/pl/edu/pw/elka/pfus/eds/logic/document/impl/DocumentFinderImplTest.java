package pl.edu.pw.elka.pfus.eds.logic.document.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DocumentFinderImplTest {
    private DocumentFinderImpl finder;
    private SecurityFacade securityFacade;
    private DocumentDao documentDao;
    private User user;
    private Document document;
    private Context context;

    @BeforeMethod
    private void setUp() {
        document = new Document();
        user = new User();
        context = mock(Context.class);

        securityFacade = mock(SecurityFacade.class);
        documentDao = mock(DocumentDao.class);
        finder = new DocumentFinderImpl(context, documentDao, securityFacade);
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testByIdForNotOwner() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);

        finder.getById(1);
    }

    @Test
    public void testByIdForSuccess() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);
        document.setOwner(user);

        assertThat(finder.getById(1)).isEqualTo(document);
    }
}
