package pl.edu.pw.elka.pfus.eds.util.ledge;

import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.objectledge.templating.TemplatingContext;
import org.objectledge.web.HttpContext;
import org.objectledge.web.mvc.MVCContext;
import pl.edu.pw.elka.pfus.eds.util.message.ListMessages;
import pl.edu.pw.elka.pfus.eds.util.message.MessageType;
import pl.edu.pw.elka.pfus.eds.util.message.Messages;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Klasa helpera dla ledge'owych obiektów.
 * Udostępnia API hermetyzucjące framework.
 */
public class LedgeHelper {
    /**
     * Dostawia wiadomość do kolekcji.
     *
     * @param context bieżący context.
     * @param type typ wiadomości.
     * @param text tekst wiadomości.
     */
    protected void postMessage(Context context, MessageType type, String text) {
        Messages messages = getMessages(context);
        messages.postMessage(type, text);
    }
    
    /**
     * Umieszcza parametr w {@link TemplatingContext}.
     *
     * @param context bieżący context.
     * @param name nazwa pod jaką ma być dostępny obiekt.
     * @param object obiekt do umieszczenia.
     */
    protected void putInTemplatingContext(Context context, String name, Object object) {
        TemplatingContext templatingContext = getTemplatingContext(context);
        templatingContext.put(name, object);
    }

    /**
     * Umieszcza parametr w sesji.
     *
     * @param context bieżący context.
     * @param name nazwa pod jaką ma być dostępny obiekt.
     * @param object obiekt do umieszczenia w sesji.
     */
    protected void putInSession(Context context, String name, Object object) {
        HttpSession session = getHttpSession(context);
        session.setAttribute(name, object);
    }

    /**
     * Wywołuje standardowe przekierowanie HTTP.
     *
     * @param context bieżący context.
     * @param url url docelowy.
     */
    protected void redirect(Context context, String url) {
        HttpContext httpContext = getHttpContext(context);
        HttpServletResponse response = httpContext.getResponse();
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Ustawia bieżący widok.
     *
     * @param context bieżący context.
     * @param view widok do ustawienia.
     */
    protected void setView(Context context, String view) {
        MVCContext mvcContext = getMVCContext(context);
        mvcContext.setView(view);
    }

    /**
     * Ustawia bieżący widok na podstawie adresu URI.
     *
     * @param context bieżący widok.
     * @param uri adres zawierający widok.
     */
    protected void setViewByUri(Context context, String uri) {
        String view = UrlHelper.getViewName(uri);
        setView(context, view);
    }

    /**
     * Zwraca string z requestu opakowany w {@link Optional}.
     *
     * @param context bieżący context.
     * @param name nazwa parametru.
     * @return opakowany string.
     */
    protected Optional<String> getStringFromRequestParameters(Context context, String name) {
        RequestParameters requestParameters = getRequestParameters(context);
        if(requestParameters.isDefined(name))
            return Optional.fromNullable(requestParameters.get(name));
        else
            return Optional.absent();
    }

    /**
     * Zwraca string z parametru lub pusty string, jeśli parametr nie istnieje.
     *
     * @param context bieżący context.
     * @param name nazwa parametru.
     * @return parametr lub pusty string.
     */
    protected String getParamOrEmptyString(Context context, String name) {
        Optional<String> optional = getStringFromRequestParameters(context, name);
        if(optional.isPresent())
            return Strings.nullToEmpty(optional.get());
        else
            return "";
    }

    /**
     * Zwraca {@link TemplatingContext} dla danego {@link Context}u.
     *
     * @param context bieżący context.
     * @return templating context.
     * @deprecated użyj lepiej metod operujących na templating context zamiast samego templating contextu.
     */
    @Deprecated
    protected TemplatingContext getTemplatingContext(Context context) {
        return TemplatingContext.getTemplatingContext(context);
    }

    /**
     * Zwraca {@link RequestParameters} dla danego {@link Context}u.
     *
     * @param context bieżący context.
     * @return request parameters.
     * @deprecated użyj lepiej metod operujących na request parameters zamiast samych parametrów żądania.
     */
    @Deprecated
    protected RequestParameters getRequestParameters(Context context) {
        return RequestParameters.getRequestParameters(context);
    }

    /**
     * Zwraca {@link HttpContext}.
     *
     * @param context bieżący context.
     * @return http context.
     * @deprecated użyj lepiej metod operujących na conteksćie http zamiast samego http contextu.
     */
    @Deprecated
    protected HttpContext getHttpContext(Context context) {
        return HttpContext.getHttpContext(context);
    }

    /**
     * Zwraca {@link HttpSession}.
     *
     * @param context bieżący context.
     * @return http session.
     * @deprecated użyj lepiej metod operujących na sesji zamiast samej sesji.
     */
    @Deprecated
    protected HttpSession getHttpSession(Context context) {
        return getHttpContext(context).getRequest().getSession();
    }

    /**
     * Zwraca {@link MVCContext}.
     *
     * @param context bieżący context.
     * @return mvc context.
     * @deprecated użyj lepiej metod operujących na context zamiast samego mvc contextu.
     */
    @Deprecated
    protected MVCContext getMVCContext(Context context) {
        return MVCContext.getMVCContext(context);
    }

    private Messages getMessages(Context context) {
        TemplatingContext templatingContext = getTemplatingContext(context);
        Messages messages = (Messages) templatingContext.get("messages");
        if(messages == null) {
            messages = new ListMessages();
            templatingContext.put("messages", messages.getCollection());
        }
        return messages;
    }
}
