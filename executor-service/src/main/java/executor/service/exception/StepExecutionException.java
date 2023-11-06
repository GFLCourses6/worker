package executor.service.exception;

public class StepExecutionException
        extends RuntimeException {

    public StepExecutionException(
            final String message,
            final String action) {
        super(String.format("%s %s%n", message, action));
    }
}
