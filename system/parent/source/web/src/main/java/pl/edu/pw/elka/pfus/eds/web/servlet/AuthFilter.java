package pl.edu.pw.elka.pfus.eds.web.servlet;

import pl.edu.pw.elka.pfus.eds.util.Constants;
import pl.edu.pw.elka.pfus.eds.util.ledge.UrlHelper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Sprawdza czy użytkownik jest uwierzytelniony. Jeśli tak, kontynuje normalnie przetwarzanie.
 * Jeśli nie, przekierowuje na formatkę logowania.
 */
public class AuthFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = getHttpServletRequest(servletRequest);
        HttpServletResponse response = getHttpServletResponse(servletResponse);

        if(shouldRedirectToLoginForm(request))
            redirectToLoginForm(request, response);
        else
            chain.doFilter(servletRequest, servletResponse);
    }

    private boolean shouldRedirectToLoginForm(HttpServletRequest request) throws IOException, ServletException {
        String requestUri = getRequestUri(request);
        return !isAuthenticated(request) && isSafeUri(requestUri);
    }

    @Override
    public void destroy() {

    }

    private HttpServletRequest getHttpServletRequest(ServletRequest servletRequest) {
        return (HttpServletRequest) servletRequest;
    }

    private HttpServletResponse getHttpServletResponse(ServletResponse servletResponse) {
        return (HttpServletResponse) servletResponse;
    }

    private String getRequestUri(ServletRequest servletRequest) {
        HttpServletRequest request = getHttpServletRequest(servletRequest);
        String requestUrl = request.getRequestURI();
        return requestUrl;
    }

    private boolean isAuthenticated(HttpServletRequest request) {
        return request.getSession().getAttribute(Constants.LOGGED_USER) != null;
    }

    private void redirectToLoginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(buildLoginFormUrl(request, response));
    }

    private boolean isSafeUri(String uri) {
        return Constants.ROOT_URL.equals(uri)
                || UrlHelper.getViewName(uri).startsWith(Constants.APP_VIEW_NAME_FRONT);
    }

    // package na potrzeby testów
    String buildLoginFormUrl(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws UnsupportedEncodingException {
        String requestUri = getRequestUri(servletRequest);
        return Constants.ROOT_URL + Constants.URL_PREFIX + Constants.AUTH_VIEW_NAME + "?" + Constants.REDIRECT_PARAM
                + "=" + URLEncoder.encode(requestUri, "UTF-8");
    }
}
