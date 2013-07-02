package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.DirectoryFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.factory.EntityFactory;
import pl.edu.pw.elka.pfus.eds.domain.dao.impl.HibernateDirectoryDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.Directory;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class DirectoryDaoTest extends IdentifableDaoTest<Directory, DirectoryDao> {
    private DirectoryDao directoryDao;
    private DirectoryFactory factory = new DirectoryFactory();

    @Override
    public DirectoryDao getDao() {
        return directoryDao;
    }

    @Override
    public void setDao(DirectoryDao dao) {
        this.directoryDao = dao;
    }

    @Override
    protected void prepareDao(SessionFactory sessionFactory, Context context) {
        directoryDao = new HibernateDirectoryDao(context, sessionFactory);
    }

    @Override
    protected EntityFactory<Directory> getFactory() {
        return factory;
    }

    @Override
    protected void updateEntity(Directory entity) {
        entity.setName(entity.getName() + entity.getName());
    }
}
