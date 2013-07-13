package pl.edu.pw.elka.pfus.eds.util.ledge;

import com.google.common.base.Optional;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.pipeline.Valve;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import pl.edu.pw.elka.pfus.eds.util.message.MessageType;

import javax.servlet.http.HttpSession;

/**
 * Helper dla akcji.
 */
public abstract class AbstractAction implements Valve {
    private LedgeHelper ledgeHelper = new LedgeHelper();

    public void postMessage(Context context, MessageType type, String text) {
        ledgeHelper.postMessage(context, type, text);
    }

    public void putInTemplatingContext(Context context, String name, Object object) {
        ledgeHelper.putInTemplatingContext(context, name, object);
    }

    public Optional<String> getStringFromRequestParameters(Context context, String name) {
        return ledgeHelper.getStringFromRequestParameters(context, name);
    }

    public String getParamOrEmptyString(Context context, String name) {
        return ledgeHelper.getParamOrEmptyString(context, name);
    }

    public void putInSession(Context context, String name, Object object) {
        ledgeHelper.putInSession(context, name, object);
    }

    public void redirect(Context context, String url) {
        ledgeHelper.redirect(context, url);
    }

    public void setView(Context context, String view) {
        ledgeHelper.setView(context, view);
    }

    public void setViewByUri(Context context, String uri) {
        ledgeHelper.setViewByUri(context, uri);
    }

    @Deprecated
    public TemplatingContext getTemplatingContext(Context context) {
        return ledgeHelper.getTemplatingContext(context);
    }

    @Deprecated
    public HttpSession getHttpSession(Context context) {
        return ledgeHelper.getHttpSession(context);
    }

    @Deprecated
    public HttpContext getHttpContext(Context context) {
        return ledgeHelper.getHttpContext(context);
    }

    @Deprecated
    public RequestParameters getRequestParameters(Context context) {
        return ledgeHelper.getRequestParameters(context);
    }

    @Deprecated
    public MVCContext getMVCContext(Context context) {
        return ledgeHelper.getMVCContext(context);
    }
}
