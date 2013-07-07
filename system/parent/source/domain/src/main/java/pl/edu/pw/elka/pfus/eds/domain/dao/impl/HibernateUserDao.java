package pl.edu.pw.elka.pfus.eds.domain.dao.impl;

import org.hibernate.Query;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.dao.IdentifableGenericDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.NamedDao;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.domain.session.SessionFactory;

public class HibernateUserDao extends IdentifableGenericDao<User> implements UserDao {
    private NamedDao<User> namedDao;

    public HibernateUserDao(Context context, SessionFactory sessionFactory) {
        super(context, sessionFactory);
        namedDao = new HibernateNamedDao<>(session, User.class);
    }

    @Override
    public User findByName(String name) {
        return namedDao.findByName(name);
    }

    @Override
    public User findByNameAndPassword(String name, String password) {
        String BY_NAME_AND_PASSWORD_QUERY = "SELECT u FROM User u WHERE u.name = :name AND u.passwordValue = :password";
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
