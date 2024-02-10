package executor.service.exception;

import executor.service.model.dto.Step;

public class StepExecutionException
        extends RuntimeException {

    public StepExecutionException(
            final String message) {
        super(message);
    }

    public StepExecutionException(
            final Step step) {
        super(String.format("The step '%s' is not supported", step.getAction()));
    }
}
