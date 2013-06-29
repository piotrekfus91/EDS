package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SessionFactoryTest {
    private SessionFactory sessionFactory = new MockSessionFactory();

    @Test
    public void hibernateSessionFactory() throws Exception {
        Context context = mock(Context.class);
        Session session = sessionFactory.getSession(context);
        HibernateSessionContext hibernateSessionContext = new HibernateSessionContext(session);

        when(context.getAttribute(HibernateSessionContext.class)).thenReturn(hibernateSessionContext);
        SessionFactory sessionFactory = new SessionFactoryImpl();

        assertThat(sessionFactory.getSession(context)).isSameAs(session);
    }

}
