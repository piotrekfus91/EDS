package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.TagDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Tag;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.EntityValidator;

public class HibernateTagDao extends IdentifableGenericDao<Tag> implements TagDao {
    private static final String BY_VALUE_QUERY =
            "SELECT t " +
            "FROM Tag t " +
            "WHERE t.value = :value";

    public HibernateTagDao(Context context, SessionFactory sessionFactory, EntityValidator validator) {
        super(context, sessionFactory, validator);
    }

    public HibernateTagDao(Session session, EntityValidator validator) {
        super(session, validator);
    }

    @Override
    public Tag findByValue(String value) {
        Query query = session.createQuery(BY_VALUE_QUERY);
        query.setString("value", value);
        return (Tag) query.uniqueResult();
    }

    @Override
    protected Class<Tag> getEntityClass() {
        return Tag.class;
    }
}
