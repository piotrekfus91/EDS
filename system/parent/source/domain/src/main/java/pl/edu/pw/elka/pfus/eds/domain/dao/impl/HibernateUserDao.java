package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.NamedDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;
import pl.edu.pw.elka.pfus.eds.domain.validator.EntityValidator;

public class HibernateUserDao extends IdentifableGenericDao<User> implements UserDao {
    private static final String BY_NAME_AND_PASSWORD_QUERY =
            "SELECT u " +
            "FROM User u " +
            "WHERE u.name = :name " +
                    "AND u.passwordValue = :password";

    private NamedDao<User> namedDao;

    public HibernateUserDao(Context context, SessionFactory sessionFactory, EntityValidator validator) {
        super(context, sessionFactory, validator);
        namedDao = new HibernateNamedDao<>(session, User.class, validator);
    }

    public HibernateUserDao(Session session, EntityValidator validator) {
        super(session, validator);
        namedDao = new HibernateNamedDao<>(session, User.class, validator);
    }

    @Override
    public User findByName(String name) {
        return namedDao.findByName(name);
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        Query query = session.createQuery(BY_NAME_AND_PASSWORD_QUERY);
        query.setString("name", name);
        query.setString("password", password);
        return (User) query.uniqueResult();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
