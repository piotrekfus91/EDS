package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;

public class GenericDao<T> {
    protected Context context;

    public GenericDao(Context context) {
        this.context = context;
    }

    public void persist(T entity) {
        getSession().persist(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Session getSession() {
        return HibernateSessionContext.getHibernateSessionContext(context).getSession();
    }
}
