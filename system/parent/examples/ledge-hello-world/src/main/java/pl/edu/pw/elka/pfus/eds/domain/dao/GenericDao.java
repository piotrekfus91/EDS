package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;
import pl.edu.pw.elka.pfus.eds.domain.entity.AbstractEntity;

public class GenericDao<T extends AbstractEntity> {
    protected Context context;

    public GenericDao(Context context) {
        this.context = context;
    }

    public void persist(T entity) {
        getSession().save(entity);
    }

    public void update(T entity) {
        getSession().update(entity);
    }

    public void persistOrUpdate(T entity) {
        getSession().saveOrUpdate(entity);
    }

    public void delete(T entity) {
        getSession().delete(entity);
    }

    protected Session getSession() {
        return HibernateSessionContext.getHibernateSessionContext(context).getSession();
    }
}
