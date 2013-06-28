package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;

public class SessionFactoryImpl implements SessionFactory {
    @Override
    public Session getSession(Context context) {
        return HibernateSessionContext.getHibernateSessionContext(context).getSession();
    }
}
