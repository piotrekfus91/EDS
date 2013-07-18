package pl.edu.pw.elka.pfus.eds.security;

import org.objectledge.authentication.AuthenticationException;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionFactory;
import org.objectledge.security.DataBackend;
import org.objectledge.security.object.hibernate.HibernateDataBackend;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.security.exception.SecurityInitializationException;
import pl.edu.pw.elka.pfus.eds.util.Constants;
import pl.edu.pw.elka.pfus.eds.util.ledge.LedgeHelper;

import java.security.Principal;

public class SecurityFacadeImpl implements SecurityFacade {
    private LedgeHelper ledgeHelper = new LedgeHelper();
    private DataBackend dataBackend;
    private UserDao userDao;

    public SecurityFacadeImpl(Context context, HibernateSessionFactory hibernateSessionFactory, UserDao userDao) {
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
            throw new SecurityException(e);
        }
    }

    @Override
    public User logIn(Context context, String login, String password) {
        try {
            Principal principal = getPrincipalByLogin(login);
            verifyPassword(principal, password);

            User candidate = userDao.findByName(login);
            if(candidate != null) {
                return preLogInUser(context, candidate);
            } else {
                throw new SecurityException("user exists in security but not in application");
            }
        } catch (AuthenticationException e) {
            throw new SecurityException(e);
        }
    }

    @Override
    public User getCurrentUser(Context context) {
        return (User) ledgeHelper.getFromSession(context, Constants.LOGGED_USER);
    }

    private Principal getPrincipalByLogin(String login) throws AuthenticationException {
        return null;
//        return userManager.getUserByLogin(login);
    }

    private void verifyPassword(Principal principal, String password) throws AuthenticationException {
//        if(!userManager.checkUserPassword(principal, password))
//            throw new InvalidLoginOrPasswordException();
    }

    private User preLogInUser(Context context, User candidate) {
        ledgeHelper.invalidateSession(context);
        ledgeHelper.putInSession(context, Constants.LOGGED_USER, candidate);
        return candidate;
    }
}
