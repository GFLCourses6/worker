package executor.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import executor.service.model.Step;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "step_result")
public class StepResult {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "step_result_seq")
    @SequenceGenerator(name = "step_result_seq", sequenceName = "step_result_seq", allocationSize = 1)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name="STEP_VALUE"))
    private Step step;

    @Column(name = "execution_status", nullable = false)
    private ExecutionStatus executionStatus;

    @Column(name = "execution_message", columnDefinition="TEXT")
    private String executionMessage = "completed";

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private ScenarioResult scenarioResult;

    public StepResult(Step step, ExecutionStatus executionStatus) {
        this.step = step;
        this.executionStatus = executionStatus;
    }

    public StepResult() {
    }

    public StepResult(Step step, String message) {
        this.executionStatus = ExecutionStatus.FAIL;
        this.executionMessage = message;
        this.step = step;
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
