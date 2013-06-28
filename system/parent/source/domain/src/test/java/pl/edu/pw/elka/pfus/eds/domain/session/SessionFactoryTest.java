package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionFactoryTest {
    @Test
    public void testHibernateSessionFactory() throws Exception {
        org.hibernate.SessionFactory hibernateSessionFactory = getSessionFactory();
        assertThat(hibernateSessionFactory).isNotNull();
    }

    @Test(dependsOnMethods = "testHibernateSessionFactory")
    public void testHibernateSession() throws Exception {
        Session session = getSession();
        assertThat(session).isNotNull();
    }

    @Test(dependsOnMethods = "testHibernateSession")
    public void hibernateSessionFactory() throws Exception {
        Session session = getSession();
        HibernateSessionContext hibernateSessionContext = new HibernateSessionContext(session);
        Context context = mock(Context.class);

        when(context.getAttribute(HibernateSessionContext.class)).thenReturn(hibernateSessionContext);
        SessionFactory sessionFactory = new SessionFactoryImpl();

        assertThat(sessionFactory.getSession(context)).isSameAs(session);
    }

    private Session getSession() {
        org.hibernate.SessionFactory hibernateSessionFactory = getSessionFactory();
        return hibernateSessionFactory.openSession();
    }

    private org.hibernate.SessionFactory getSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
