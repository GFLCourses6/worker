package executor.service.service.step.impl;

import executor.service.exception.StepExecutionException;
import executor.service.service.step.StepExecution;

import java.util.Arrays;

public enum StepExecutionType {
    CLICKCSS(new StepExecutionClickCss()),
    CLICKXPATH(new StepExecutionClickXpath()),
    SLEEP(new StepExecutionSleep());

    private final StepExecution stepExecution;

    StepExecutionType(final StepExecution stepExecution) {
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
                .orElseThrow(() -> new StepExecutionException(
                        String.format("The step '%s' is not supported", action)));
    }
}
