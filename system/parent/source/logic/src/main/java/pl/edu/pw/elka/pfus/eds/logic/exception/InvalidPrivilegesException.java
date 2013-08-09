package pl.edu.pw.elka.pfus.eds.logic.exception;

public class InvalidPrivilegesException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Nieprawidłowe uprawnienia";

    public InvalidPrivilegesException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidPrivilegesException(String message) {
        super(message);
    }

    public InvalidPrivilegesException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidPrivilegesException(Throwable cause) {
        super(cause);
    }

    public InvalidPrivilegesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
