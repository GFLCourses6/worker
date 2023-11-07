package executor.service.exception;

public class StepExecutionInterruptedException
        extends RuntimeException {
    public StepExecutionInterruptedException(
            final String message,
            final Throwable cause) {
        super(message, cause);
    }
}
