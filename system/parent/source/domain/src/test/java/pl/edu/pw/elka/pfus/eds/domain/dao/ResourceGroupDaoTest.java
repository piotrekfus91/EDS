package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.ResourceGroupFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.UserFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateResourceGroupDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.ResourceGroup;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class ResourceGroupDaoTest extends IdentifableDaoTest<ResourceGroup, ResourceGroupDao> {
    private ResourceGroupDao resourceGroupDao;
    private ResourceGroupFactory factory = new ResourceGroupFactory();
    private UserFactory userFactory = new UserFactory();

    @Test
    public void testGetFoundersGroup() throws Exception {
        ResourceGroup resourceGroup = factory.getSampleEntity();
        User user = userFactory.getSampleEntity();
        resourceGroup.setFounder(user);
        resourceGroupDao.persist(resourceGroup);

        List<ResourceGroup> resourceGroups = resourceGroupDao.getAllOfFounder(user);
        assertThat(resourceGroups).containsExactly(resourceGroup);
    }

    @Override
    public ResourceGroupDao getDao() {
        return resourceGroupDao;
    }

    @Override
    public void setDao(ResourceGroupDao dao) {
        this.resourceGroupDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        resourceGroupDao = new HibernateResourceGroupDao(context, sessionFactory);
    }

    @Override
    protected EntityFactory<ResourceGroup> getFactory() {
        return  factory;
    }

    @Override
    protected void updateEntity(ResourceGroup entity) {
        entity.setName(entity.getName() + entity.getName());
        User newUser = userFactory.getSampleEntity();
        newUser.setName("resource_group_test");
        entity.setFounder(newUser);
    }
}
