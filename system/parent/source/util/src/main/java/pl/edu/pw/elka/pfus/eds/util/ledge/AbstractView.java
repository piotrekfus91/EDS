package pl.edu.pw.elka.pfus.eds.util.ledge;

import com.google.common.base.Optional;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import org.objectledge.web.mvc.builders.AbstractBuilder;
import pl.edu.pw.elka.pfus.eds.util.message.MessageType;

import javax.servlet.http.HttpSession;

/**
 * Klasa abstrakcyjna jako baza dla wszystkich akcji.
 * Jej główny fakt istnienia to hermetyzacja frameworka.
 */
public abstract class AbstractView extends AbstractBuilder {
    private LedgeHelper ledgeHelper = new LedgeHelper();

    protected AbstractView(Context context) {
        super(context);
    }
    
    public void postMessage(MessageType type, String text) {
        ledgeHelper.postMessage(context, type, text);
    }

    public void putInTemplatingContext(String name, Object object) {
        ledgeHelper.putInTemplatingContext(context, name, object);
    }

    public Optional<String> getStringFromRequestParameters(String name) {
        return ledgeHelper.getStringFromRequestParameters(context, name);
    }

    public String getParamOrEmptyString(String name) {
        return ledgeHelper.getParamOrEmptyString(context, name);
    }

    public void putInSession(String name, Object object) {
        ledgeHelper.putInSession(context, name, object);
    }
    
    public Object getFromSession(String name) {
        return ledgeHelper.getFromSession(context, name);
    }
    
    public void setView(String view) {
        ledgeHelper.setView(context, view);
    }
    
    public void setViewByUri(String uri) {
        ledgeHelper.setViewByUri(context, uri);
    }
    
    public void redirect(String url) {
        ledgeHelper.redirect(context, url);
    }

    @Deprecated
    public TemplatingContext getTemplatingContext() {
        return ledgeHelper.getTemplatingContext(context);
    }

    @Deprecated
    public HttpSession getHttpSession() {
        return ledgeHelper.getHttpSession(context);
    }

    @Deprecated
    public HttpContext getHttpContext() {
        return ledgeHelper.getHttpContext(context);
    }

    @Deprecated
    public RequestParameters getRequestParameters() {
        return ledgeHelper.getRequestParameters(context);
    }

    public MVCContext getMVCContext() {
        return ledgeHelper.getMVCContext(context);
    }
}
