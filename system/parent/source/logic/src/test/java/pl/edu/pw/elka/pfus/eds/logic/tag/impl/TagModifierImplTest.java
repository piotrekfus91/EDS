package pl.edu.pw.elka.pfus.eds.logic.tag.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Document;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.tag.TagFinder;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TagModifierImplTest {
    private TagModifierImpl tagModifier;
    private DocumentDao documentDao;
    private TagDao tagDao;
    private SecurityFacade securityFacade;
    private TagFinder tagFinder;
    private User user;
    private Document document;
    private Context context;

    @BeforeMethod
    private void setUp() {
        user = new User();
        document = new Document();
        documentDao = mock(DocumentDao.class);
        tagDao = mock(TagDao.class);
        context = mock(Context.class);
        securityFacade = mock(SecurityFacade.class);
        tagModifier = new TagModifierImpl(context, securityFacade, documentDao, tagDao);
    }

    @Test
    public void testSplittingByComma() throws Exception {
        String string = "first, second, , third ";
        assertThat(tagModifier.splitListByComma(string)).containsOnly("first", "second", "third");
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testSettingListForNotOwner() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(documentDao.findById(anyInt())).thenReturn(document);

        tagModifier.addTagsToDocument(1, "");
    }
}
