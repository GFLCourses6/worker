package executor.service.service.step;

import executor.service.model.ExecutionStatus;
import executor.service.model.Step;
import executor.service.model.StepResult;

public abstract class AbstractStepExecution implements StepExecution {

    protected StepResult computeStepResult(Step step, Runnable function) {
        StepResult stepResult = new StepResult(step);
        try {
            function.run();
            stepResult.setExecutionStatus(ExecutionStatus.SUCCESS);
        } catch (Exception e) {
            stepResult.setExecutionMessage(e.getMessage());
            stepResult.setExecutionStatus(ExecutionStatus.FAIL);
        }
        return stepResult;
    }
}
