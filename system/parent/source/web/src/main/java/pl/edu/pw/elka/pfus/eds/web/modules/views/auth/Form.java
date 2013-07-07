package pl.edu.pw.elka.pfus.eds.web.modules.views.auth;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.TemplatingContext;
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
        TemplatingContext templatingContext = getTemplatingContext();
        RequestParameters requestParameters = getRequestParameters();
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        String redirect = requestParameters.get(Constants.REDIRECT_PARAM);
        templatingContext.put(Constants.REDIRECT_PARAM, redirect);
        logger.info("displaying login form with redirect: " + redirect);
        return super.build(template, embeddedBuildResults);
    }
}
