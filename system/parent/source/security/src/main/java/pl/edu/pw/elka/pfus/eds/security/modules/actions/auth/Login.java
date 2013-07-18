package pl.edu.pw.elka.pfus.eds.security.modules.actions.auth;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.ledge.SecurityAction;
import pl.edu.pw.elka.pfus.eds.util.Constants;
import pl.edu.pw.elka.pfus.eds.util.message.MessageType;

public class Login extends SecurityAction {
    private static final Logger logger = Logger.getLogger(Login.class);
    private SecurityFacade securityFacade;

    public Login(SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        if(isAuthenticated(context))
            return;

        String login = getParamOrEmptyString(context, Constants.LOGIN_NAME);
        String password = getParamOrEmptyString(context, Constants.PASSWORD_VALUE);

        try {
            securityFacade.logIn(context, login, password);
            postMessage(context, MessageType.SUCCESS, "Logowanie udane.");
            logger.info("login succeeded: " + login);
            redirect(context, Constants.ROOT_URL);
        } catch (pl.edu.pw.elka.pfus.eds.security.exception.SecurityException e) {
            postMessage(context, MessageType.ERROR, "Logowanie nie powiodło się.");
            logger.warn("login failed: " + e.getMessage(), e);
        }
    }
}
