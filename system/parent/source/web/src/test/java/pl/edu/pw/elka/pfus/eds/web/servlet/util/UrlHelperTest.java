package pl.edu.pw.elka.pfus.eds.web.servlet.util;

import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

public class UrlHelperTest {
    @Test
    public void testEmptyViewName() throws Exception {
        assertThat(UrlHelper.getViewName("/")).isEmpty();
    }

    @Test
    public void testNotEmptyViewName() throws Exception {
        assertThat(UrlHelper.getViewName("/eds/v/View")).isEqualTo("View");
        assertThat(UrlHelper.getViewName("/eds/v/some.View")).isEqualTo("some.View");
    }

    @Test
    public void testNotEmptyWithQueryString() throws Exception {
        assertThat(UrlHelper.getViewName("/?param=value")).isEmpty();
        assertThat(UrlHelper.getViewName("/eds/v/some.View?param=value")).isEqualTo("some.View");
    }
}
