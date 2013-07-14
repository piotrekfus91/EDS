package pl.edu.pw.elka.pfus.eds.security.ledge;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.util.Constants;
import pl.edu.pw.elka.pfus.eds.util.ledge.AbstractAction;

import javax.servlet.http.HttpSession;

public abstract class SecurityAction extends AbstractAction {
    protected boolean isAuthenticated(Context context) {
        return getFromSession(context, Constants.LOGGED_USER) != null;
    }

    protected void invalidateSession(Context context) {
        HttpSession session = getHttpSession(context);
        session.invalidate();
    }
}
