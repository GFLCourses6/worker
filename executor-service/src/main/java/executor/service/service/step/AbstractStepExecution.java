package executor.service.service.step;

import executor.service.model.Step;
import executor.service.model.entity.ExecutionStatus;
import executor.service.model.entity.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public abstract class AbstractStepExecution implements StepExecution {

    protected abstract void doStepLogic(WebDriver webDriver, Step step) throws Exception;

    @Override
    public StepResult step(WebDriver webDriver, Step step) {
        StepResult stepResult = new StepResult(step, ExecutionStatus.SUCCESS);

        try {
            doStepLogic(webDriver, step);
        } catch (WebDriverException e) {
            handleStepExecutionException(stepResult, e.getRawMessage());
        } catch (Exception e) {
            handleStepExecutionException(stepResult, e.getMessage());
        }
        return stepResult;
    }

    private void handleStepExecutionException(StepResult stepResult, String message) {
        stepResult.setExecutionMessage(message);
        stepResult.setExecutionStatus(ExecutionStatus.FAIL);
    }
}
