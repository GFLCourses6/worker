package executor.service.service.step.impl;

import executor.service.model.entity.ExecutionStatus;
import executor.service.model.Step;
import executor.service.model.entity.StepResult;
import executor.service.service.step.AbstractStepExecution;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static executor.service.service.executor.Action.SLEEP_ACTION;

public class StepExecutionSleep extends AbstractStepExecution {

    @Override
    public String getStepAction() {
        return SLEEP_ACTION;
    }

    @Override
    public StepResult step(WebDriver webDriver, Step step) {
        StepResult result = new StepResult(step);
        try {
            long value = Long.parseLong(step.getValue());
            TimeUnit.SECONDS.sleep(value);
            result.setExecutionStatus(ExecutionStatus.SUCCESS);
        } catch (Exception e) {
            result.setExecutionStatus(ExecutionStatus.FAIL);
            result.setExecutionMessage(e.getMessage());
        }
        return result;
    }
}
