package executor.service.service.step;

import executor.service.model.Step;
import executor.service.model.entity.ExecutionStatus;
import executor.service.model.entity.StepResult;
import org.openqa.selenium.WebDriverException;

public abstract class AbstractStepExecution implements StepExecution {

    protected StepResult computeStepResult(Step step, Runnable function) {
        StepResult stepResult = new StepResult(step, ExecutionStatus.SUCCESS);
        String message;

        try {
            function.run();
        } catch (WebDriverException e) {
            message = e.getRawMessage();
            handleExecutionException(stepResult, message);
        } catch (Exception e) {
            message = e.getMessage();
            handleExecutionException(stepResult, message);
        }
        return stepResult;
    }

    private void handleExecutionException(StepResult stepResult, String message) {
        stepResult.setExecutionMessage(message);
        stepResult.setExecutionStatus(ExecutionStatus.FAIL);
    }
}
