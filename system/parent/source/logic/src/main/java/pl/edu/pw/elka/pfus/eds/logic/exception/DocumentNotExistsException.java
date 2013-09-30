package pl.edu.pw.elka.pfus.eds.logic.exception;

/**
 * Wyjątek oznaczający, że dokument nie istnieje.
 */
public class DocumentNotExistsException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Wybrany dokument już nie istnieje";

    public DocumentNotExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public DocumentNotExistsException(String message) {
        super(message);
    }

    public DocumentNotExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentNotExistsException(Throwable cause) {
        super(cause);
    }

    public DocumentNotExistsException(String message, Throwable cause, boolean enableSuppression,
                                      boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
