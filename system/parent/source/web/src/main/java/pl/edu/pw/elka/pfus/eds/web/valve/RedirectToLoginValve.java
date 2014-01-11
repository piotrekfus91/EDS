package pl.edu.pw.elka.pfus.eds.web.valve;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.pipeline.Valve;
import org.objectledge.web.mvc.MVCContext;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.config.Config;

public class RedirectToLoginValve implements Valve {
    private static final Logger logger = Logger.getLogger(RedirectToLoginValve.class);
    private SecurityFacade securityFacade;
    private String loginView;
    private String restrictedViewPrefix;

    public RedirectToLoginValve(Config config, SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
        loginView = config.getString("auth_view_name");
        restrictedViewPrefix = config.getString("app_view_name_front");
    }

    @Override
    public void process(Context context) throws ProcessingException {
        MVCContext mvcContext = MVCContext.getMVCContext(context);

        String requestedView = mvcContext.getView();
        if(isRestricted(requestedView) && !isLogged(context))
            redirectToLogin(mvcContext);
    }

    private boolean isLogged(Context context) {
        return securityFacade.isLogged(context);
    }

    public boolean isRestricted(String view) {
        if(Strings.isNullOrEmpty(view))
            return true;

        return view.startsWith(restrictedViewPrefix);
    }

    private void redirectToLogin(MVCContext mvcContext) {
        mvcContext.setView(loginView);
    }
}
