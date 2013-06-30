package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.MimeTypeDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.MimeType;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateMimeTypeDao extends IdentifableGenericDao<MimeType> implements MimeTypeDao {
    public HibernateMimeTypeDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    protected Class<MimeType> getEntityClass() {
        return MimeType.class;
    }
}
