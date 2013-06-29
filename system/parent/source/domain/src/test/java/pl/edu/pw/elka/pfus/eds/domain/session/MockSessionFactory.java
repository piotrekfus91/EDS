package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;
import org.objectledge.context.Context;

public class MockSessionFactory implements SessionFactory {
    @Override
    public Session getSession(Context context) {
        return getHibernateSessionFactory().openSession();
    }

    private org.hibernate.SessionFactory getHibernateSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }
}
