package pl.edu.pw.elka.pfus.eds.security.exception;

public class UserNotLoggedException extends SecurityException {
    private static final String DEFAULT_MESSAGE = "Użytkownik nie jest zalogowany";

    public UserNotLoggedException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotLoggedException(String message) {
        super(message);
    }

    public UserNotLoggedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotLoggedException(Throwable cause) {
        super(cause);
    }

    public UserNotLoggedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
