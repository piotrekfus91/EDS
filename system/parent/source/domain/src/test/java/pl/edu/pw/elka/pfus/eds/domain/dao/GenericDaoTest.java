package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactoryImpl;
import pl.edu.pw.elka.pfus.eds.domain.validator.MockEntityValidator;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GenericDaoTest {
    @Test
    public void testGetParametrizedTypeClass() throws Exception {
        Context context = mock(Context.class);
        Session session = mock(Session.class);
        SessionFactory sessionFactory = new SessionFactoryImpl();
        HibernateSessionContext hibernateSessionContext = new HibernateSessionContext(session);
        when(context.getAttribute(HibernateSessionContext.class)).thenReturn(hibernateSessionContext);

        GenericDao<User> genericUserDao = new GenericDao<User>(context, sessionFactory, new MockEntityValidator()) {
            @Override
            protected Class<User> getEntityClass() {
                return User.class;
            }
        };

        assertThat(genericUserDao.getEntityClass()).isEqualTo(User.class);
    }
}
