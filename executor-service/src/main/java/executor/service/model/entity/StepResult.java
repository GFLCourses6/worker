package executor.service.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import executor.service.model.dto.Step;
import jakarta.persistence.*;

import java.time.Instant;
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
    private String executionMessage;

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate = Instant.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "scenario_id")
    private ScenarioResult scenarioResult;

    public StepResult() {
    }

    public StepResult(Step step, ExecutionStatus executionStatus) {
        this.step = step;
        this.executionStatus = executionStatus;
    }

    public StepResult(Step step, String message, ExecutionStatus executionStatus) {
        this.executionStatus = executionStatus;
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

    public Instant getCreatedDate() {
        return createdDate;
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
