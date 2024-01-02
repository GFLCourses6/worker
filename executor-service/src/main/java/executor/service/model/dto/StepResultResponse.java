package executor.service.model.dto;

import executor.service.model.entity.ExecutionStatus;

import java.time.Instant;
import java.util.Objects;

public class StepResultResponse {

    private Long id;
    private String action;
    private String value;
    private ExecutionStatus executionStatus;
    private String executionMessage;
    private Instant createdDate;

    public StepResultResponse(Long id, String action, String value,
                              ExecutionStatus executionStatus, String executionMessage,
                              Instant createdDate) {
        this.id = id;
        this.action = action;
        this.value = value;
        this.executionStatus = executionStatus;
        this.executionMessage = executionMessage;
        this.createdDate = createdDate;
    }

    public StepResultResponse() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StepResultResponse that)) return false;
        return Objects.equals(id, that.id)
                && Objects.equals(action, that.action)
                && Objects.equals(value, that.value)
                && executionStatus == that.executionStatus
                && Objects.equals(executionMessage, that.executionMessage)
                && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, action, value, executionStatus, executionMessage, createdDate);
    }

    @Override
    public String toString() {
        return "StepResultResponse{" +
                "id=" + id +
                ", action='" + action + '\'' +
                ", value='" + value + '\'' +
                ", executionStatus=" + executionStatus +
                ", executionMessage='" + executionMessage + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
