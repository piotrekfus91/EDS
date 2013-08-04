package pl.edu.pw.elka.pfus.eds.domain.dao;

import com.google.common.primitives.Ints;
import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

import java.util.List;

public abstract class GenericDao<T extends GenericEntity> implements Dao<T> {
    protected Session session;

    public GenericDao(Session session) {
        this.session = session;
    }

    public GenericDao(Context context, SessionFactory sessionFactory) {
        session = sessionFactory.getSession(context);
    }

    @Override
    public void persist(T entity) {
        session.saveOrUpdate(entity);
    }

    @Override
    public T merge(T entity) {
        return (T) session.merge(entity);
    }

    @Override
    public void delete(T entity) {
        session.delete(entity);
    }

    @Override
    public int count() {
        String COUNT_QUERY = "SELECT COUNT(*) FROM " + getEntityClass().getCanonicalName() + " e";
        Query query = session.createQuery(COUNT_QUERY);
        long result = (long) query.uniqueResult();
        return Ints.checkedCast(result);
    }

    @Override
    public List<T> getAll() {
        String ALL_QUERY = "SELECT e FROM " + getEntityClass().getCanonicalName() + " e";
        Query query = session.createQuery(ALL_QUERY);
        return query.list();
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void beginTransaction() {
        session.getTransaction().begin();
    }

    @Override
    public void commitTransaction() {
        session.getTransaction().commit();
    }

    @Override
    public void rollbackTransaction() {
        session.getTransaction().rollback();
    }

    protected abstract Class<T> getEntityClass();
}
