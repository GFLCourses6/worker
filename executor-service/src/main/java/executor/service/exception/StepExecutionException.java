package executor.service.exception;

public class StepExecutionException
        extends RuntimeException {

    public StepExecutionException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }

    public StepExecutionException(
            final String message) {
        super(message);
    }
}
