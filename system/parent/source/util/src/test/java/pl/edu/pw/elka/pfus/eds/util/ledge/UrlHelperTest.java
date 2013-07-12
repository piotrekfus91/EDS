package pl.edu.pw.elka.pfus.eds.util.ledge;

import org.fest.assertions.Assertions;
import org.testng.annotations.Test;

public class UrlHelperTest {
    @Test
    public void testEmptyViewName() throws Exception {
        Assertions.assertThat(UrlHelper.getViewName("/")).isEmpty();
    }

    @Test
    public void testNotEmptyViewName() throws Exception {
        Assertions.assertThat(UrlHelper.getViewName("/eds/v/View")).isEqualTo("View");
        Assertions.assertThat(UrlHelper.getViewName("/eds/v/some.View")).isEqualTo("some.View");
    }

    @Test
    public void testNotEmptyWithQueryString() throws Exception {
        Assertions.assertThat(UrlHelper.getViewName("/?param=value")).isEmpty();
        Assertions.assertThat(UrlHelper.getViewName("/eds/v/some.View?param=value")).isEqualTo("some.View");
    }
}
