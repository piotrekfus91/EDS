package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.DirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateDirectoryDao extends IdentifableGenericDao<Directory> implements DirectoryDao {
    public HibernateDirectoryDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
    }

    @Override
    protected Class<Directory> getEntityClass() {
        return Directory.class;
    }
}
