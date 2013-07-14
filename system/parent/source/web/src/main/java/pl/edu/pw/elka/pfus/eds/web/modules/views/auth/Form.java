package pl.edu.pw.elka.pfus.eds.web.modules.views.auth;

import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.web.mvc.builders.BuildException;
import pl.edu.pw.elka.pfus.eds.util.Constants;
import pl.edu.pw.elka.pfus.eds.util.ledge.AbstractView;

/**
 * Widok używany przy logowaniu użytkownika.
 */
public class Form extends AbstractView {
    private static final Logger logger = Logger.getLogger(Form.class);

    public Form(Context context) {
        super(context);
    }

    @Override
    public String build(Template template, String embeddedBuildResults) throws BuildException, ProcessingException {
        if(isAuthenticated() && getMVCContext().getAction() != "auth.Login")
            redirect(Constants.ROOT_URL);

        putLoginFormFieldNames();

        Optional<String> redirectCandidate = getStringFromRequestParameters(Constants.REDIRECT_PARAM);
        String redirect;
        if(redirectCandidate.isPresent())
            redirect = redirectCandidate.get();
        else
            redirect = Constants.ROOT_URL;

        putInTemplatingContext(Constants.REDIRECT_PARAM, redirect);
        logger.info("displaying login form with redirect: " + redirect);

        return super.build(template, embeddedBuildResults);
    }

    private boolean isAuthenticated() {
        return getFromSession(Constants.LOGGED_USER) != null;
    }

    private void putLoginFormFieldNames( ) {
        putInTemplatingContext("loginName", Constants.LOGIN_NAME);
        putInTemplatingContext("passwordValue", Constants.PASSWORD_VALUE);
    }
}
