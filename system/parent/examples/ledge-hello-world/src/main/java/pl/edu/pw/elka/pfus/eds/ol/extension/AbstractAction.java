package pl.edu.pw.elka.pfus.eds.ol.extension;

import org.hibernate.Session;
import org.objectledge.context.Context;
import org.objectledge.hibernate.HibernateSessionContext;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.Valve;
import org.objectledge.templating.TemplatingContext;

public abstract class AbstractAction implements Valve {
    protected RequestParameters getRequestParameters(Context context) {
        return RequestParameters.getRequestParameters(context);
    }

    protected Session getSession(Context context) {
        return HibernateSessionContext.getHibernateSessionContext(context).getSession();
    }

    protected TemplatingContext getTemplatingContext(Context context) {
        return TemplatingContext.getTemplatingContext(context);
    }
}
