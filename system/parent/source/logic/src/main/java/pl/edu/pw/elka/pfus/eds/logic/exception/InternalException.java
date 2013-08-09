package pl.edu.pw.elka.pfus.eds.logic.exception;

/**
 * Informacja o błędzie wewnętrznym aplikacji.
 */
public class InternalException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Błąd wewnętrzny";

    public InternalException() {
        super(DEFAULT_MESSAGE);
    }

    public InternalException(String message) {
        super(message);
    }

    public InternalException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalException(Throwable cause) {
        super(cause);
    }

    public InternalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
