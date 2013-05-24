package pl.edu.pw.elka.pfus.eds.domain.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;

import java.util.List;

public class UserDao extends GenericDao<User> {
    private static final Logger logger = Logger.getLogger(UserDao.class);

    public UserDao(Context context) {
        super(context);
    }

    public List<User> getAllUsers() {
        String queryString = "SELECT u FROM User u";
        Query query = getSession().createQuery(queryString);
        logger.warn(query.list());
        return query.list();
    }
}
