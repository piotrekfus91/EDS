package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.UserFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateUserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import static org.fest.assertions.Assertions.assertThat;

public class UserDaoTest extends IdentifableDaoTest<User, UserDao> {
    private UserDao dao;
    private UserFactory factory = new UserFactory();

    @Test
    public void testLockedAfterPersisting() throws Exception{
        User user = getSampleEntity();
        getDao().persist(user);
        assertThat(user.isLocked()).isFalse();
    }

    @Test
    public void testLockedAfterLocking() throws Exception {
        User user = getSampleEntity();
        getDao().persist(user);
        user.setLocked(true);
        getDao().persist(user);
        assertThat(user.isLocked()).isTrue();
    }

    @Test
    public void testByNameReturningNull() throws Exception {
        User user = getDao().findByName("");
        assertThat(user).isNull();
    }

    @Test
    public void testByName() throws Exception {
        User user = getSampleEntity();
        getDao().persist(user);

        User sameUser = getDao().findByName(user.getName());
        assertThat(sameUser).isEqualTo(user);
    }

    @Override
    public UserDao getDao() {
        return dao;
    }

    @Override
    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    protected EntityFactory<User> getFactory() {
        return factory;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        setDao(new HibernateUserDao(context, sessionFactory));
    }

    @Override
    protected void updateEntity(User entity) {
        entity.setName(entity.getName() + entity.getName());
    }
}