package executor.service.exception;

public class FileReadException
        extends RuntimeException {
    public FileReadException(
            final String message) {
        super(message);
    }

    public FileReadException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }
}
