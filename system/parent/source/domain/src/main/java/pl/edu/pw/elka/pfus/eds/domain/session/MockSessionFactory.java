package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.objectledge.context.Context;

/**
 * Mock w main? Tak - potrzebny też w logice.
 */
public class MockSessionFactory implements SessionFactory {
    @Override
    public Session getSession(Context context) {
        return getHibernateSessionFactory().openSession();
    }

    private org.hibernate.SessionFactory getHibernateSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
