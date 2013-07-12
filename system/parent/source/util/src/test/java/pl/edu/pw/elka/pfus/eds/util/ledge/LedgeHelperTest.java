package pl.edu.pw.elka.pfus.eds.util.ledge;

import org.objectledge.context.Context;
import org.objectledge.parameters.RequestParameters;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class LedgeHelperTest {
    private LedgeHelper ledgeHelper = new LedgeHelper();
    private Context context;
    private RequestParameters requestParameters;

    @BeforeMethod
    public void beforeMethod() {
        requestParameters = new MockRequestParameters();
        context = mock(Context.class);
        when(context.getAttribute(RequestParameters.class)).thenReturn(requestParameters);
    }

    @Test
    public void getNotExistingParam() throws Exception {
        assertThat(ledgeHelper.getStringFromRequestParameters(context, "some attr").isPresent()).isFalse();
    }

    @Test
    public void getNullString() throws Exception {
        requestParameters.add("attr", (String)null);

        assertThat(ledgeHelper.getStringFromRequestParameters(context, "attr").isPresent()).isFalse();
    }

    @Test
    public void getEmptyString() throws Exception {
        requestParameters.add("attr", "");

        assertThat(ledgeHelper.getStringFromRequestParameters(context, "attr").isPresent()).isTrue();
        assertThat(ledgeHelper.getStringFromRequestParameters(context, "attr").get()).isEmpty();
    }

    @Test
    public void getSampleString() throws Exception {
        requestParameters.add("attr", "sample");

        assertThat(ledgeHelper.getStringFromRequestParameters(context, "attr").get()).isEqualTo("sample");
    }

    @Test
    public void getEmptyStringFromNotExistingParam() throws Exception {
        assertThat(ledgeHelper.getParamOrEmptyString(context, "attr")).isEmpty();
    }

    @Test
    public void getExistingString() throws Exception {
        requestParameters.add("attr", "sample");

        assertThat(ledgeHelper.getParamOrEmptyString(context, "attr")).isEqualTo("sample");
    }
}
