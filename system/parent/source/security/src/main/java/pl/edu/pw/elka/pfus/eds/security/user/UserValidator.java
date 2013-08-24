package pl.edu.pw.elka.pfus.eds.security.user;

import org.objectledge.context.Context;
import pl.edu.pw.elka.pfus.eds.security.exception.UserNotLoggedException;

public class UserValidator {
    private UserManager userManager;

    public UserValidator(UserManager userManager) {
        this.userManager = userManager;
    }

    public void enforceLogin(Context context) {
        if(!userManager.isLogged(context))
            throw new UserNotLoggedException();
    }
}
