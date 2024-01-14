package executor.service.service.step;

import executor.service.model.dto.Step;
import executor.service.model.entity.ExecutionStatus;
import executor.service.model.entity.StepResult;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public abstract class AbstractStepExecution implements StepExecution {

    protected abstract void executeStepLogic(WebDriver webDriver, Step step) throws Exception;

    @Override
    public StepResult step(WebDriver webDriver, Step step) {
        try {
            executeStepLogic(webDriver, step);
            return new StepResult(step, ExecutionStatus.SUCCESS);
        } catch (WebDriverException e) {
            return new StepResult(step, e.getRawMessage(), ExecutionStatus.FAIL);
        } catch (Exception e) {
            return new StepResult(step, e.getMessage(), ExecutionStatus.FAIL);
        }
    }
}
