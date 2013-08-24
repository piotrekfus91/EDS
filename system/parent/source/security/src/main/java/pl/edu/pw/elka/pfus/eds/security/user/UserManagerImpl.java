package pl.edu.pw.elka.pfus.eds.security.user;

import org.apache.log4j.Logger;
import org.objectledge.authentication.AuthenticationException;
import org.objectledge.authentication.PasswordDigester;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionFactory;
import org.objectledge.security.DataBackend;
import org.objectledge.security.exception.DataBackendException;
import org.objectledge.security.object.SecurityUser;
import org.objectledge.security.object.hibernate.HibernateDataBackend;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.security.exception.InvalidLoginOrPasswordException;
import pl.edu.pw.elka.pfus.eds.security.exception.SecurityException;
import pl.edu.pw.elka.pfus.eds.security.exception.SecurityInitializationException;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.ledge.LedgeHelper;

import javax.servlet.http.HttpServletRequest;

public class UserManagerImpl implements UserManager {
    private static final Logger logger = Logger.getLogger(UserManagerImpl.class);

    private Config config;
    private LedgeHelper ledgeHelper = new LedgeHelper();
    private PasswordDigester passwordDigester;
    private DataBackend dataBackend;
    private UserDao userDao;

    public UserManagerImpl(Context context, HibernateSessionFactory hibernateSessionFactory, Config config,
                              PasswordDigester passwordDigester, UserDao userDao) {
        this.config = config;
        this.passwordDigester = passwordDigester;
        this.userDao = userDao;
        try {
            dataBackend = new HibernateDataBackend(context, hibernateSessionFactory);
        } catch (Exception e) {
            throw new SecurityInitializationException(e);
        }
    }

    @Override
    public void createUser(String login, String firstName, String lastName, String password) {
        try {
            dataBackend.createAccount(login, firstName, lastName, password);
        } catch (Exception e) {
            throw new pl.edu.pw.elka.pfus.eds.security.exception.SecurityException(e);
        }
    }

    @Override
    public User logIn(Context context, String login, String password) {
        try {
            SecurityUser user = dataBackend.getUserByName(login);
            logger.info("found user: " + user.getName());
            verifyPassword(user, password);

            User candidate = userDao.findByName(login);
            if(candidate != null) {
                return preLogInUser(context, candidate);
            } else {
                throw new SecurityException("user exists in security but not in application");
            }
        } catch (AuthenticationException | DataBackendException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public User getCurrentUser(Context context) {
        return (User) ledgeHelper.getFromSession(context, config.getString("logged_user"));
    }

    @Override
    public User getCurrentUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(config.getString("logged_user"));
    }

    @Override
    public boolean isLogged(Context context) {
        return getCurrentUser(context) != null;
    }

    private void verifyPassword(SecurityUser user, String password) throws AuthenticationException {
        String passwordDigest = passwordDigester.digestPassword(password);
        if(user.getPassword().equals(passwordDigest))
            throw new InvalidLoginOrPasswordException();
    }

    private User preLogInUser(Context context, User candidate) {
        ledgeHelper.invalidateSession(context);
        ledgeHelper.putInSession(context, config.getString("logged_user"), candidate);
        return candidate;
    }
}
