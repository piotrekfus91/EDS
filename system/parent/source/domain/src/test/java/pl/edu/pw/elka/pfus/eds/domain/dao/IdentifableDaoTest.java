package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.entity.IdentifableEntity;
import pl.edu.pw.elka.pfus.eds.domain.session.MockSessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public abstract class IdentifableDaoTest<E extends IdentifableEntity, T extends IdentifableDao> {
    public abstract T getDao();
    public abstract void setDao(T dao);
    protected abstract void prepareDao(SessionFactory sessionFactory, Context context);
    protected abstract EntityFactory<E> getFactory();
    protected abstract void updateEntity(E entity);

    @BeforeMethod
    protected void beforeMethod() {
        SessionFactory sessionFactory = new MockSessionFactory();
        Context context = mock(Context.class);
        prepareDao(sessionFactory, context);
        Session session = getDao().getSession();
        session.getTransaction().begin();
    }

    @AfterMethod
    protected void afterMethod() {
        Session session = getDao().getSession();
        session.getTransaction().rollback();
    }

    @Test
    public void testPersistingNew() throws Exception {
        E entity = getSampleEntity();
        int oldEntitiesCount = getDao().count();

        getDao().persist(entity);

        assertThat(getDao().count()).isGreaterThan(oldEntitiesCount);
    }

    @Test
    public void testUpdating() throws Exception {
        E entity = getSampleEntity();
        getDao().persist(entity);
        int oldNumberOfUsers = getDao().count();

        updateEntity(entity);
        getDao().persist(entity);

        assertThat(getDao().count()).isEqualTo(oldNumberOfUsers);
        E updatedEntity = (E) getDao().findById(entity.getId());
        assertEntities(updatedEntity, entity);
    }

    @Test
    public void testEmptyCount() throws Exception {
        assertThat(getDao().count()).isEqualTo(0);
    }

    @Test
    public void testNotEmptyCount() throws Exception {
        E entity = getSampleEntity();
        E entity2 = getSampleEntity();
        updateEntity(entity2);
        getDao().persist(entity);
        getDao().persist(entity2);

        assertThat(getDao().count()).isEqualTo(2);
    }

    @Test
    public void testEmptyAll() throws Exception {
        assertThat(getDao().getAll()).isEmpty();
    }

    @Test
    public void testAll() throws Exception {
        E entity = getSampleEntity();
        E entity2 = getSampleEntity();
        updateEntity(entity2);
        getDao().persist(entity);
        getDao().persist(entity2);

        assertThat(getDao().getAll()).containsExactly(entity, entity2);
    }

    protected void assertEntities(E actual, E expected) {
        assertThat(actual).isEqualTo(expected);
    }

    protected E getSampleEntity() {
        return getFactory().getSampleEntity();
    }
}
