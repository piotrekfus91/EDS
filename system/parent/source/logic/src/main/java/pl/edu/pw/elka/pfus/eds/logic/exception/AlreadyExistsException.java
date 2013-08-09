package pl.edu.pw.elka.pfus.eds.logic.exception;

public class AlreadyExistsException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Istnieje już plik lub katalog o podanej nazwie";

    public AlreadyExistsException() {
        super(DEFAULT_MESSAGE);
    }

    public AlreadyExistsException(String message) {
        super(message);
    }

    public AlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public AlreadyExistsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
