package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import pl.edu.pw.elka.pfus.eds.domain.entity.GenericEntity;
import pl.edu.pw.elka.pfus.eds.domain.entity.Named;

public class NamedGenericDao<T extends GenericEntity & Named> extends GenericDao<T> implements NamedDao<T> {
    private Class<T> clazz;

    public NamedGenericDao(Session session, Class<T> clazz) {
        super(session);
        this.clazz = clazz;
    }

    @Override
    public T findByName(String name) {
        String BY_NAME_QUERY = "SELECT u FROM " + getEntityClass().getCanonicalName() + " u WHERE u.name = :name";
        Query query = session.createQuery(BY_NAME_QUERY);
        query.setString("name", name);
        return (T) query.uniqueResult();
    }

    @Override
    protected Class<T> getEntityClass() {
        return clazz;
    }
}
