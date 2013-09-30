package pl.edu.pw.elka.pfus.eds.web.modules.views.auth;

import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.web.mvc.builders.BuildException;
import pl.edu.pw.elka.pfus.eds.security.SecurityFacade;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.ledge.AbstractView;

/**
 * Widok używany przy logowaniu użytkownika.
 */
public class Form extends AbstractView {
    private static final Logger logger = Logger.getLogger(Form.class);
    private Config config;
    private SecurityFacade securityFacade;

    public Form(Context context, Config config, SecurityFacade securityFacade) {
        super(context);
        this.config = config;
        this.securityFacade = securityFacade;
    }

    @Override
    public String build(Template template, String embeddedBuildResults) throws BuildException, ProcessingException {
        putLoginFormFieldNames();

        Optional<String> redirectCandidate = getStringFromRequestParameters(config.getString("redirect_param"));
        String redirect;
        if(redirectCandidate.isPresent())
            redirect = redirectCandidate.get();
        else
            redirect = config.getString("root_url");

        putInTemplatingContext(config.getString("redirect_param"), redirect);
        logger.info("displaying login form with redirect: " + redirect);

        return super.build(template, embeddedBuildResults);
    }

    private void putLoginFormFieldNames( ) {
        putInTemplatingContext("loginName", config.getString("login_name"));
        putInTemplatingContext("passwordValue", config.getString("password_value"));
    }
}
