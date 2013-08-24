package pl.edu.pw.elka.pfus.eds.logic.resource.group.impl;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.ResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.logic.exception.ObjectNotFoundException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResourceGroupFinderImplTest {
    private ResourceGroupFinderImpl finder;
    private Context context;
    private ResourceGroupDao resourceGroupDao;
    private SecurityFacade securityFacade;
    private User user;
    private ResourceGroup resourceGroup;

    @BeforeMethod
    private void setUp() {
        resourceGroupDao = mock(ResourceGroupDao.class);
        context = mock(Context.class);
        securityFacade = mock(SecurityFacade.class);
        finder = new ResourceGroupFinderImpl(context, securityFacade, resourceGroupDao);

        user = new User();
        resourceGroup = new ResourceGroup();
    }

    @Test(expectedExceptions = ObjectNotFoundException.class)
    public void testGetByNameWithDocumentsForNotFound() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(null);

        finder.getByNameWithDocuments("");
    }

    @Test
    public void testGetByNameWithDocumentsForSuccess() throws Exception {
        when(resourceGroupDao.findByName(anyString())).thenReturn(resourceGroup);

        ResourceGroup found = finder.getByNameWithDocuments("");

        assertThat(found).isEqualTo(resourceGroup);
    }
}
