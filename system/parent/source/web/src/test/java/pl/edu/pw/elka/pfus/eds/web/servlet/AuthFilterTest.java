package pl.edu.pw.elka.pfus.eds.web.servlet;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.util.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthFilterTest {
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

        String expectedUri = Constants.ROOT_URL + Constants.URL_PREFIX + Constants.AUTH_VIEW_NAME + "?redirect="
                + URLEncoder.encode("/", Constants.ENCODING);
        assertThat(filter.buildLoginFormUrl(request, response)).isEqualTo(expectedUri);
    }

    @Test
    public void testBuilLoginFormSomeView() throws Exception {
        String viewName = "some.View";
        when(request.getRequestURI()).thenReturn(Constants.URL_PREFIX + viewName);

        String encodedRequestedUri = URLEncoder.encode(Constants.URL_PREFIX + viewName, Constants.ENCODING);
        String expectedUri = Constants.ROOT_URL + Constants.URL_PREFIX + Constants.AUTH_VIEW_NAME + "?" +
                Constants.REDIRECT_PARAM + "=" + encodedRequestedUri;

        assertThat(filter.buildLoginFormUrl(request, response)).isEqualTo(expectedUri);
    }

    @Test
    public void testBuilLoginFormQueryString() throws Exception {
        String viewName = "some.View";
        String queryString = "param=value";
        String requestUri = Constants.URL_PREFIX + viewName + "?" + queryString;
        when(request.getRequestURI()).thenReturn(requestUri);

        String encodedRequestedUri = URLEncoder.encode(requestUri, "UTF-8");
        String expectedUri = Constants.ROOT_URL + Constants.URL_PREFIX + Constants.AUTH_VIEW_NAME + "?" + Constants.REDIRECT_PARAM
                + "=" + encodedRequestedUri;
        assertThat(filter.buildLoginFormUrl(request, response)).isEqualTo(expectedUri);
    }
}
