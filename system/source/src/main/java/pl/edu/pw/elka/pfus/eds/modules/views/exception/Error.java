package pl.edu.pw.elka.pfus.eds.modules.views.exception;

import org.apache.log4j.Logger;
import org.objectledge.context.Context;
import org.objectledge.pipeline.ProcessingException;
import org.objectledge.templating.Template;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.mvc.builders.AbstractBuilder;
import org.objectledge.web.mvc.builders.BuildException;

public class Error extends AbstractBuilder {
    private static final Logger logger = Logger.getLogger(Error.class);

    public Error(Context context) {
        super(context);
    }

    @Override
    public String build(Template template, String embeddedBuildResults) throws BuildException, ProcessingException {
        logger.info("constructing Error");
        TemplatingContext templatingContext = TemplatingContext.getTemplatingContext(context);
        return super.build(template, embeddedBuildResults);
    }
}
