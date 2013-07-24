package pl.edu.pw.elka.pfus.eds.security.valve;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.pipeline.Valve;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;

public class CheckLoginValve implements Valve {
    private static final Logger logger = Logger.getLogger(CheckLoginValve.class);
    private SecurityFacade securityFacade;

    public CheckLoginValve(SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
    }

    @Override
    public void process(Context context) throws ProcessingException {
        if(!securityFacade.isLogged(context)) {
            logger.warn("user is not logged");
            throw new ProcessingException("User is not logged");
        } else {
            logger.info("user is logged: " + securityFacade.getCurrentUser(context).getName());
        }
    }
}
