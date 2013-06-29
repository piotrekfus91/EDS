package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.NamedDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.NamedGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateUserDao extends IdentifableGenericDao<User> implements UserDao {
    private NamedDao<User> namedDao;

    public HibernateUserDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
        namedDao = new NamedGenericDao<>(session, User.class);
    }

    @Override
    public User findByName(String name) {
        return namedDao.findByName(name);
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
