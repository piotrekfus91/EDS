package pl.edu.pw.elka.pfus.eds.util.ledge;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.Valve;
import org.objectledge.web.HttpContext;

import javax.servlet.http.HttpSession;

/**
 * Helper dla akcji.
 */
public abstract class AbstractAction implements Valve {
    protected HttpContext getHttpContext(Context context) {
        return HttpContext.getHttpContext(context);
    }

    protected HttpSession getHttpSession(Context context) {
        return getHttpContext(context).getRequest().getSession();
    }

    protected void putInSession(Context context, String name, Object object) {
        HttpSession session = getHttpSession(context);
        session.setAttribute(name, object);
    }

    protected RequestParameters getRequestParameters(Context context) {
        return RequestParameters.getRequestParameters(context);
    }

    protected Optional<String> getStringFromRequestParameters(Context context, String name) {
        RequestParameters requestParameters = getRequestParameters(context);
        if(requestParameters.isDefined(name))
            return Optional.fromNullable(requestParameters.get(name));
        else
            return Optional.absent();
    }

    protected String getParamOrEmptyString(Context context, String name) {
        Optional<String> optional = getStringFromRequestParameters(context, name);
        if(optional.isPresent())
            return Strings.nullToEmpty(optional.get());
        else
            return "";
    }
}
