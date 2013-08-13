package pl.edu.pw.elka.pfus.eds.logic.exception;

public class InvalidMimeTypeException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Niedozwolony typ MIME";

    public InvalidMimeTypeException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidMimeTypeException(String message) {
        super(message);
    }

    public InvalidMimeTypeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMimeTypeException(Throwable cause) {
        super(cause);
    }

    public InvalidMimeTypeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
