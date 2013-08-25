package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ResourceGroupModifierImplTest {
    private ResourceGroupModifierImpl modifier;
    private Context context;
    private ResourceGroupDao resourceGroupDao;
    private UserDao userDao;
    private SecurityFacade securityFacade;
    private ResourceGroup resourceGroup;
    private User user;

    @BeforeMethod
    public void setUp() throws Exception {
        context = mock(Context.class);
        securityFacade = mock(SecurityFacade.class);
        resourceGroupDao = mock(ResourceGroupDao.class);
        userDao = mock(UserDao.class);
        modifier = new ResourceGroupModifierImpl(context, securityFacade, resourceGroupDao, userDao);
    }

    @Test
    public void testCreatingForSuccess() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        ResourceGroup result = modifier.create("name", "desc");

        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getDescription()).isEqualTo("desc");
    }

    @Test(expectedExceptions = InternalException.class)
    public void testCreatingForRollback() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        doThrow(new InternalException()).when(resourceGroupDao).commitTransaction();

        modifier.create("", "");
    }
}
