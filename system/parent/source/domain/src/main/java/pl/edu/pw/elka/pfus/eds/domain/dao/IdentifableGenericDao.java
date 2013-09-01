package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.entity.IdentifableEntity;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public abstract class IdentifableGenericDao<T extends IdentifableEntity> extends GenericDao<T> implements IdentifableDao<T> {
    public IdentifableGenericDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    protected IdentifableGenericDao(Session session) {
        super(session);
    }

    @Override
    public T findById(Integer id) {
        String BY_ID_QUERY = "SELECT e FROM " + getEntityClass().getCanonicalName() + " e WHERE e.id = :id";
        Query query = session.createQuery(BY_ID_QUERY);
        query.setInteger("id", id);
        return (T) query.uniqueResult();
    }
}
