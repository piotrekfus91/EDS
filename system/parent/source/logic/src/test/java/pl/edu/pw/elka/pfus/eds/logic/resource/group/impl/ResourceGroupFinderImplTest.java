package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.DocumentDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
import pl.edu.pw.elka.pfus.eds.logic.resource.group.dto.ResourceGroupWithAssignedUsers;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.privilege.PrivilegeService;
import pl.edu.pw.elka.pfus.eds.security.privilege.Privileges;

import java.util.LinkedList;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceGroupFinderImplTest {
    private ResourceGroupFinderImpl finder;
    private Context context;
    private ResourceGroupDao resourceGroupDao;
    private UserDao userDao;
    private DocumentDao documentDao;
    private DirectoryDao directoryDao;
    private SecurityFacade securityFacade;
    private PrivilegeService privilegeService;
    private User user;
    private ResourceGroup resourceGroup;

    @BeforeMethod
    private void setUp() {
        resourceGroupDao = mock(ResourceGroupDao.class);
        context = mock(Context.class);
        securityFacade = mock(SecurityFacade.class);
        privilegeService = mock(PrivilegeService.class);
        userDao = mock(UserDao.class);
        documentDao = mock(DocumentDao.class);
        directoryDao = mock(DirectoryDao.class);
        finder = new ResourceGroupFinderImpl(context, securityFacade, privilegeService, resourceGroupDao, userDao,
                documentDao, directoryDao);

        user = new User();
        resourceGroup = new ResourceGroup();
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testGetByNameWithDocumentsForNotFound() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(null);

        finder.getByNameWithUsers("");
    }

    @Test
    public void testGetByNameWithDocumentsForSuccess() throws Exception {
        when(resourceGroupDao.findById(anyInt())).thenReturn(resourceGroup);
        when(securityFacade.getCurrentUser(context)).thenReturn(user);
        List<Integer> intList = new LinkedList<>();
        intList.add(1);
        when(resourceGroupDao.getIdsOfNames(anyList())).thenReturn(intList);
        when(privilegeService.hasPrivilege(user.getName(), Privileges.DELETE, "")).thenReturn(true);

        ResourceGroupWithAssignedUsers found = finder.getByNameWithUsers("");

        assertThat(found.getResourceGroup()).isEqualTo(resourceGroup);
    }
}
