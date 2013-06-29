package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateUserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.MockSessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.Date;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class UserDaoTest {
    private UserDao userDao;

    @BeforeMethod
    private void beforeMethod() {
        SessionFactory sessionFactory = new MockSessionFactory();
        Context context = mock(Context.class);
        userDao = new HibernateUserDao(context, sessionFactory);
        Session session = userDao.getSession();
        session.getTransaction().begin();
    }

    @AfterMethod
    private void afterMethod() {
        Session session = userDao.getSession();
        session.getTransaction().rollback();
    }

    @Test
    public void testPersistingNew() throws Exception {
        User user = getSampleUser();
        int oldNumberOfUsers = userDao.count();

        userDao.persist(user);

        assertThat(userDao.count()).isGreaterThan(oldNumberOfUsers);
    }

    @Test
    public void testUpdating() throws Exception {
        User user = getSampleUser();
        userDao.persist(user);
        int oldNumberOfUsers = userDao.count();

        user.setName("new name");
        userDao.persist(user);

        assertThat(userDao.count()).isEqualTo(oldNumberOfUsers);
        User updatedUser = userDao.findById(user.getId());
        assertThat(updatedUser.getName()).isEqualTo(user.getName());
    }

    @Test
    public void testLockedAfterPersisting() throws Exception{
        User user = getSampleUser();
        userDao.persist(user);
        assertThat(user.isLocked()).isFalse();
    }

    @Test
    public void testLockedAfterLocking() throws Exception {
        User user = getSampleUser();
        userDao.persist(user);
        user.setLocked(true);
        userDao.persist(user);
        assertThat(user.isLocked()).isTrue();
    }

    @Test
    public void testEmptyCount() throws Exception {
        assertThat(userDao.count()).isEqualTo(0);
    }

    @Test
    public void testNotEmptyCount() throws Exception {
        User user = getSampleUser();
        User user2 = getSampleUser();
        user2.setName(user.getName() + user.getName());
        userDao.persist(user);
        userDao.persist(user2);

        assertThat(userDao.count()).isEqualTo(2);
    }

    @Test
    public void testByNameReturningNull() throws Exception {
        User user = userDao.findByName("");
        assertThat(user).isNull();
    }

    @Test
    public void testByName() throws Exception {
        User user = getSampleUser();
        userDao.persist(user);

        User sameUser = userDao.findByName(user.getName());
        assertThat(sameUser).isEqualTo(user);
    }

    @Test
    public void testEmptyAll() throws Exception {
        assertThat(userDao.getAll()).isEmpty();
    }

    @Test
    public void testAll() throws Exception {
        User user = getSampleUser();
        User user2 = getSampleUser();
        user2.setName(user.getName() + user.getName());
        userDao.persist(user);
        userDao.persist(user2);
        assertThat(userDao.getAll()).containsExactly(user, user2);
    }

    private User getSampleUser() {
        User user = new User();
        user.setName("login name");
        user.setPasswordValue("");
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setCreated(new Date());
        return user;
    }
}
