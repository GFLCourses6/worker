package executor.service.exception;

public class StepExecutionException
        extends RuntimeException {

    public StepExecutionException(
            final String message) {
        super(message);
    }
}
