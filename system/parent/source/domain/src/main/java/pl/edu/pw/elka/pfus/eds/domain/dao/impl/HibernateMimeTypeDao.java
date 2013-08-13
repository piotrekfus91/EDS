package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateMimeTypeDao extends IdentifableGenericDao<MimeType> implements MimeTypeDao {
    private static final String FIND_BY_NAME_QUERY =
            "SELECT mt " +
            "FROM MimeType mt " +
            "WHERE mt.type = :type";

    public HibernateMimeTypeDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    public MimeType findByType(String type) {
        Query query = session.createQuery(FIND_BY_NAME_QUERY);
        query.setString("type", type);
        return (MimeType) query.uniqueResult();
    }

    @Override
    protected Class<MimeType> getEntityClass() {
        return MimeType.class;
    }
}
