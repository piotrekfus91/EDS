package pl.edu.pw.elka.pfus.eds.web.servlet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.config.Config;
import pl.edu.pw.elka.pfus.eds.util.config.impl.DefaultClassLoaderPropertiesConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthFilterTest {
    private Config config = new DefaultClassLoaderPropertiesConfig();
    private HttpServletRequest request;
    private HttpServletResponse response;
    private AuthFilter filter = new AuthFilter();

    @BeforeMethod
    private void beforeMethod() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    public void testBuildLoginFormRootUrl() throws Exception {
        when(request.getRequestURI()).thenReturn("/");

        String expectedUri = config.getString("root_url") + config.getString("url_prefix") +
                config.getString("auth_view_name") + "?redirect=" +
                URLEncoder.encode("/", config.getString("encoding"));

        assertThat(filter.buildLoginFormUrl(request, response)).isEqualTo(expectedUri);
    }

    @Test
    public void testBuildLoginFormSomeView() throws Exception {
        String viewName = "some.View";
        when(request.getRequestURI()).thenReturn(config.getString("url_prefix") + viewName);

        String encodedRequestedUri = URLEncoder.encode(config.getString("url_prefix")
                + viewName, config.getString("encoding"));

        String expectedUri = config.getString("root_url") + config.getString("url_prefix") +
                config.getString("auth_view_name") + "?" + config.getString("redirect_param") + "="
                + encodedRequestedUri;

        assertThat(filter.buildLoginFormUrl(request, response)).isEqualTo(expectedUri);
    }

    @Test
    public void testBuildLoginFormQueryString() throws Exception {
        String viewName = "some.View";
        String queryString = "param=value";
        String requestUri = config.getString("url_prefix") + viewName + "?" + queryString;
        when(request.getRequestURI()).thenReturn(requestUri);

        String encodedRequestedUri = URLEncoder.encode(requestUri, "UTF-8");
        String expectedUri = config.getString("root_url") + config.getString("url_prefix") +
                config.getString("auth_view_name") + "?" + config.getString("redirect_param")
                + "=" + encodedRequestedUri;
        assertThat(filter.buildLoginFormUrl(request, response)).isEqualTo(expectedUri);
    }
}
