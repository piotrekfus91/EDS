package pl.edu.pw.elka.pfus.eds.security.exception;

/**
 * Wyjątek zgłąszany gdy podane login i hasło nie są poprawne.
 */
public class InvalidLoginOrPasswordException extends SecurityException {
    public InvalidLoginOrPasswordException() {
    }

    public InvalidLoginOrPasswordException(String message) {
        super(message);
    }

    public InvalidLoginOrPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginOrPasswordException(Throwable cause) {
        super(cause);
    }

    public InvalidLoginOrPasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
