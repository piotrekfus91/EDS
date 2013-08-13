package pl.edu.pw.elka.pfus.eds.logic.exception;

public class MimeTypeDetectionException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Błąd podczas sprawdzania typu MIME";

    public MimeTypeDetectionException() {
        super(DEFAULT_MESSAGE);
    }

    public MimeTypeDetectionException(String message) {
        super(message);
    }

    public MimeTypeDetectionException(String message, Throwable cause) {
        super(message, cause);
    }

    public MimeTypeDetectionException(Throwable cause) {
        super(DEFAULT_MESSAGE, cause);
    }

    public MimeTypeDetectionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
