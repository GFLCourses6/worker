package executor.service.model;

import java.util.Objects;

public class StepResult {

    private Step step;
    private ExecutionStatus executionStatus;
    private String executionMessage;

    public StepResult(Step step) {
        this.step = step;
    }

    public StepResult() {
    }

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public ExecutionStatus getExecutionStatus() {
        return executionStatus;
    }

    public void setExecutionStatus(ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
    }

    public String getExecutionMessage() {
        return executionMessage;
    }

    public void setExecutionMessage(String executionMessage) {
        this.executionMessage = executionMessage;
    }

    @Override
    public String toString() {
        return "StepResult{" +
                "step=" + step +
                ", executionStatus=" + executionStatus +
                ", executionMessage='" + executionMessage + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StepResult that)) return false;
        return Objects.equals(step, that.step)
                && executionStatus == that.executionStatus
                && Objects.equals(executionMessage, that.executionMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(step, executionStatus, executionMessage);
    }
}
