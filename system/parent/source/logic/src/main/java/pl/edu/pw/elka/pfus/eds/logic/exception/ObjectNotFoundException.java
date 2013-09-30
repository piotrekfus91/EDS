package pl.edu.pw.elka.pfus.eds.logic.exception;

/**
 * Wyjątek oznaczający, że nastąpiła próba dostępu do niestniejącego obiektu,
 * np. grupy zasobów lub katalogu.
 */
public class ObjectNotFoundException extends LogicException {
    private static final String DEFAULT_MESSAGE = "Obiekt nie został odnaleziony";

    public ObjectNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public ObjectNotFoundException(String message) {
        super(message);
    }

    public ObjectNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectNotFoundException(Throwable cause) {
        super(cause);
    }

    public ObjectNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
