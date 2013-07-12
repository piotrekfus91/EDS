package pl.edu.pw.elka.pfus.eds.domain.session;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;

public class SessionFactoryImpl implements SessionFactory {
    @Override
    public Session getSession(Context context) {
        if(context == null)
            return getBasicSession();

        HibernateSessionContext hibernateSessionContext = getHibernateSessionContext(context);
        if(hibernateSessionContext != null)
            return hibernateSessionContext.getSession();
        else
            return getBasicSession();
    }

    private HibernateSessionContext getHibernateSessionContext(Context context) {
        return HibernateSessionContext.getHibernateSessionContext(context);
    }

    private Session getBasicSession() {
        return HibernateUtil.getInstance().getSession();
    }
}
