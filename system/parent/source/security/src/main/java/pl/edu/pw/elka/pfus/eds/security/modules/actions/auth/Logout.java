package pl.edu.pw.elka.pfus.eds.security.modules.actions.auth;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.security.ledge.SecurityAction;
import pl.edu.pw.elka.pfus.eds.util.config.Config;

/**
 * Akcja uruchamiana przy wylogowaniu użytkownika.
 */
public class Logout extends SecurityAction {
    private static final Logger logger = Logger.getLogger(Logout.class);
    private Config config;

    public Logout(Config config, SecurityFacade securityFacade) {
        super(securityFacade);
        this.config = config;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        if(!isAuthenticated(context))
            return;

        logger.info("logout");
        invalidateSession(context);
        redirect(context, config.getString("root_url"));
    }
}
