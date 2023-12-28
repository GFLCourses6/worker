package executor.service.model.entity;

import executor.service.model.Step;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "step_result")
public class StepResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name="STEP_VALUE"))
    private Step step;

    @Column(name = "execution_status", nullable = false)
    private ExecutionStatus executionStatus;

    @Column(name = "execution_message", nullable = false)
    private String executionMessage;

    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private ScenarioResult scenarioResult;

    public StepResult(Step step) {
        this.step = step;
    }

    public StepResult() {
    }

    public Long getId() {
        return id;
    }

    public ScenarioResult getScenarioResult() {
        return scenarioResult;
    }

    public void setScenarioResult(ScenarioResult scenarioResult) {
        this.scenarioResult = scenarioResult;
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
