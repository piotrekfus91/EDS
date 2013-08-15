package pl.edu.pw.elka.pfus.eds.web.modules.views.app;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.web.mvc.builders.BuildException;
import pl.edu.pw.elka.pfus.eds.domain.entity.User;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.ledge.AbstractView;

public class UserInfo extends AbstractView {
    private static final Logger logger = Logger.getLogger(UserInfo.class);
    private SecurityFacade securityFacade;

    public UserInfo(Context context, SecurityFacade securityFacade) {
        super(context);
        this.securityFacade = securityFacade;
    }

    @Override
    public String build(Template template, String embeddedBuildResults) throws BuildException, ProcessingException {
        User currentUser = securityFacade.getCurrentUser(context);
        logger.info("preparing user info: " + currentUser);
        putInTemplatingContext("currentUser", currentUser);
        return super.build(template, embeddedBuildResults);
    }
}
