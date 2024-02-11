package executor.service.exception;

import executor.service.model.dto.Step;

public class StepNotFoundException
        extends RuntimeException {

    public StepNotFoundException(
            final String message) {
        super(message);
    }

    public StepNotFoundException(
            final Step step) {
        super(String.format("The step '%s' is not supported", step.getAction()));
    }
}
