package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class GenericDao<T extends GenericEntity> implements Dao<T> {
    private Session session;

    public GenericDao(Context context, SessionFactory sessionFactory) {
        session = sessionFactory.getSession(context);
    }

    @Override
    public void persist(T entity) {
        session.saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        session.delete(entity);
    }
}
