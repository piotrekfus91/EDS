package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.InternalException;
import pl.edu.pw.elka.pfus.eds.logic.exception.InvalidPrivilegesException;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
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
        resourceGroup = new ResourceGroup();
        user = new User();
        modifier = new ResourceGroupModifierImpl(context, securityFacade, privilegeService, resourceGroupDao, userDao);
    }

    @Test
    public void testCreatingForSuccess() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(userDao.findById(anyInt())).thenReturn(user);

        ResourceGroup result = modifier.create("name", "desc");

        assertThat(result.getName()).isEqualTo("name");
        assertThat(result.getDescription()).isEqualTo("desc");
    }

    @Test(expectedExceptions = InternalException.class)
    public void testCreatingForRollback() throws Exception {
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(userDao.findById(anyInt())).thenReturn(user);
        doThrow(new InternalException()).when(resourceGroupDao).commitTransaction();

        modifier.create("", "");
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testUpdateForNotExisting() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(null);

        modifier.updateNameAndDescription("", "", "");
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testUpdateForNotOwner() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(resourceGroup);
        when(userDao.findById(anyInt())).thenReturn(user);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        modifier.updateNameAndDescription("", "", "");
    }

    @Test
    public void testUpdateForSuccess() throws Exception {
        when(resourceGroupDao.findByName("starting name")).thenReturn(resourceGroup);
        when(userDao.findById(anyInt())).thenReturn(user);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        when(resourceGroupDao.merge(resourceGroup)).thenReturn(resourceGroup);
        resourceGroup.setFounder(user);

        resourceGroup.setName("starting name");
        resourceGroup.setDescription("starting description");

        ResourceGroup result = modifier.updateNameAndDescription("starting name", "ending name", "ending description");

        assertThat(result.getName()).isEqualTo("ending name");
        assertThat(result.getDescription()).isEqualTo("ending description");

        verify(resourceGroupDao, times(1)).commitTransaction();
    }

    @Test(expectedExceptions = InternalException.class)
    public void testUpdateForRollback() throws Exception {
        when(resourceGroupDao.findByName("starting name")).thenReturn(resourceGroup);
        when(userDao.findById(anyInt())).thenReturn(user);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        doThrow(new InternalException()).when(resourceGroupDao).commitTransaction();
        resourceGroup.setFounder(user);

        resourceGroup.setName("starting name");
        resourceGroup.setDescription("starting description");

        modifier.updateNameAndDescription("starting name", "ending name", "ending description");
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testDeletingForNotFound() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(null);

        modifier.delete("");
    }

    @Test(expectedExceptions = InvalidPrivilegesException.class)
    public void testDeletingForNotOwner() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(resourceGroup);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);

        modifier.delete("");
    }

    @Test
    public void testDeletingForSuccess() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(resourceGroup);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        resourceGroup.setFounder(user);

        modifier.delete("");

        verify(resourceGroupDao, times(1)).commitTransaction();
    }

    @Test(expectedExceptions = InternalException.class)
    public void testDeletingForRollback() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(resourceGroup);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        resourceGroup.setFounder(user);
        doThrow(new InternalException()).when(resourceGroupDao).commitTransaction();

        modifier.delete("");
    }
}
