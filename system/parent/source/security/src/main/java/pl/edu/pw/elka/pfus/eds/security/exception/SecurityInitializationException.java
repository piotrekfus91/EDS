package pl.edu.pw.elka.pfus.eds.security.exception;

public class SecurityInitializationException extends SecurityException {
    public SecurityInitializationException() {
    }

    public SecurityInitializationException(String message) {
        super(message);
    }

    public SecurityInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public SecurityInitializationException(Throwable cause) {
        super(cause);
    }

    public SecurityInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
