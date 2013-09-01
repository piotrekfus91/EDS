package pl.edu.pw.elka.pfus.eds.security.user;

import org.objectledge.context.Context;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pl.edu.pw.elka.pfus.eds.security.exception.UserNotLoggedException;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserValidatorTest {
    private UserValidator userValidator;
    private UserManager userManager;
    private Context context;

    @BeforeMethod
    private void setUp() {
        context = mock(Context.class);
        userManager = mock(UserManager.class);
        userValidator = new UserValidator(userManager);
    }

    @Test(expectedExceptions = UserNotLoggedException.class)
    public void testEnsureLoginForNotLogged() throws Exception {
        when(userManager.isLogged(context)).thenReturn(false);

        userValidator.enforceLogin(context);
    }

    @Test
    public void testEnsureLoginForLogged() throws Exception {
        when(userManager.isLogged(context)).thenReturn(true);

        userValidator.enforceLogin(context);

        assertThat(true).isTrue();
    }
}
