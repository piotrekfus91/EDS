package pl.edu.pw.elka.pfus.eds.util.ledge;

import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.mvc.builders.AbstractBuilder;

public abstract class AbstractView extends AbstractBuilder {
    public AbstractView(Context context) {
        super(context);
    }

    protected TemplatingContext getTemplatingContext() {
        return TemplatingContext.getTemplatingContext(context);
    }

    protected RequestParameters getRequestParameters() {
        return RequestParameters.getRequestParameters(context);
    }
}
