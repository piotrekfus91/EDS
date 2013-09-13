package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateUserDao;
import pl.edu.pw.elka.pfus.eds.domain.session.MockSessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;

public class SecurityFacadeTest {
    private UserDao userDao;

    @BeforeMethod
    public void beforeMethod() {
        Context context = mock(Context.class);
        SessionFactory sessionFactory = new MockSessionFactory();
        userDao = new HibernateUserDao(context, sessionFactory, null);
        assertThat(userDao).isNotNull();
    }

    @Test
    public void logInWithUserNotExists() throws Exception {

    }
}
