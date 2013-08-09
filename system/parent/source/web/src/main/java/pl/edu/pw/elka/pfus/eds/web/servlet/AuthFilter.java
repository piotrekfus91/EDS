package pl.edu.pw.elka.pfus.eds.web.servlet;

import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.impl.DefaultClassLoaderPropertiesConfig;
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
    private Config config = new DefaultClassLoaderPropertiesConfig();

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
        return request.getSession().getAttribute(config.getString("logged_user")) != null;
    }

    private void redirectToLoginForm(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendRedirect(buildLoginFormUrl(request, response));
    }

    private boolean isSafeUri(String uri) {
        return config.getString("root_url").equals(uri)
                || UrlHelper.getViewName(uri).startsWith(config.getString("app_view_name_front"));
    }

    // package na potrzeby testów
    String buildLoginFormUrl(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
            throws UnsupportedEncodingException {
        String requestUri = getRequestUri(servletRequest);
        return config.getString("root_url") + config.getString("url_prefix") + config.getString("auth_view_name") + "?"
                + config.getString("redirect_param") + "=" + URLEncoder.encode(requestUri, config.getString("encoding"));
    }
}
