package pl.edu.pw.elka.pfus.eds.security.ledge;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.ledge.AbstractAction;

import javax.servlet.http.HttpSession;

public abstract class SecurityAction extends AbstractAction {
    protected SecurityFacade securityFacade;

    protected SecurityAction(SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
    }

    protected boolean isAuthenticated(Context context) {
        return securityFacade.isLogged(context);
    }

    protected void invalidateSession(Context context) {
        HttpSession session = getHttpSession(context);
        session.invalidate();
    }
}
