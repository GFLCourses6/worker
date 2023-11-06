package executor.service.service.executor;

import executor.service.exception.StepExecutionException;
import executor.service.service.step.StepExecution;
import executor.service.service.step.impl.StepExecutionClickCss;

import java.util.Arrays;

public enum StepExecutionType {
    CLICKCSS(new StepExecutionClickCss());
    private final StepExecution stepExecution;

    StepExecutionType(
            final StepExecution stepExecution) {
        this.stepExecution = stepExecution;
    }

    public StepExecution getStepExecution() {
        return this.stepExecution;
    }

    public static StepExecutionType fromString(
            final String action) {
        return Arrays
                .stream(values())
                .filter(type -> type.name().equals(action.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> (new StepExecutionException(
                        "The action %s is not supported", action)));
    }
}
