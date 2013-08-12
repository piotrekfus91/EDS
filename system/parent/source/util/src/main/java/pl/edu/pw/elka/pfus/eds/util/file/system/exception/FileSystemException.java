package pl.edu.pw.elka.pfus.eds.util.file.system.exception;

/**
 * Wyjątek ogólny operacji na systemie plików.
 */
public class FileSystemException extends RuntimeException {
    public FileSystemException() {

    }

    public FileSystemException(String message) {
        super(message);
    }

    public FileSystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileSystemException(Throwable cause) {
        super(cause);
    }

    public FileSystemException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
