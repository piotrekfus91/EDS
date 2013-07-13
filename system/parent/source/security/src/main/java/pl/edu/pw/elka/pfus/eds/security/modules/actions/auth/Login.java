package pl.edu.pw.elka.pfus.eds.security.modules.actions.auth;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import pl.edu.pw.elka.pfus.eds.domain.dao.UserDao;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.util.Constants;
import pl.edu.pw.elka.pfus.eds.util.ledge.AbstractAction;
import pl.edu.pw.elka.pfus.eds.util.message.MessageType;

import java.util.List;

public class Login extends AbstractAction {
    private static final Logger logger = Logger.getLogger(Login.class);
    private UserDao userDao;

    public Login(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        if(isAuthenticated(context))
            return;

        String login = getParamOrEmptyString(context, Constants.LOGIN_NAME);
        String password = getParamOrEmptyString(context, Constants.PASSWORD_VALUE);

        registerUserInSession(context, login, password);
    }

    private boolean isAuthenticated(Context context) {
        return getFromSession(context, Constants.LOGGED_USER) != null;
    }

    private void registerUserInSession(Context context, String login, String password) {
        User user = findUser(login, password);

        List<User> users = userDao.getAll();
        System.out.println(users);

        if(user != null) {
            putUserInSession(context, user);
            postMessage(context, MessageType.SUCCESS, "Zalogowano: " + user.getFriendlyName() + ".");
            redirectToRequestedViewOrDefault(context);
            logger.info("User " + user.getName() + " successfully logged in");
        } else {
            postMessage(context, MessageType.ERROR, "Logowanie nie powiodło się.");
            logger.info("Failed to log user with login <" + login + "> and password <" + password + ">");
        }
    }

    private User findUser(String login, String password) {
        return userDao.findByNameAndPassword(login, password);
    }

    private void redirectToRequestedViewOrDefault(Context context) {
        String redirect = getParamOrEmptyString(context, Constants.REDIRECT_PARAM);
        if(Strings.isNullOrEmpty(redirect)) {
            setView(context, Constants.ROOT_URL);
        } else {
            redirect(context, redirect);
        }
    }

    private void putUserInSession(Context context, User user) {
        putInSession(context, Constants.LOGGED_USER, user);
    }
}
