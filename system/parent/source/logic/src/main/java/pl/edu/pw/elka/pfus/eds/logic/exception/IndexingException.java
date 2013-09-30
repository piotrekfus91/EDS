package pl.edu.pw.elka.pfus.eds.logic.exception;

/**
 * Wyjątek oznaczający błąd podczas indeksowania dokumentów.
 */
public class IndexingException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Błąd podczas indeksowania dokumentu";

    public IndexingException() {
        super(DEFAULT_MESSAGE);
    }

    public IndexingException(String message) {
        super(message);
    }

    public IndexingException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexingException(Throwable cause) {
        super(cause);
    }

    public IndexingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
