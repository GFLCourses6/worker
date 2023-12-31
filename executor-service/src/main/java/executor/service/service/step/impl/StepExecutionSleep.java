package executor.service.service.step.impl;

import executor.service.model.Step;
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
    protected void executeStepLogic(WebDriver webDriver, Step step) throws Exception {
        long value = Long.parseLong(step.getValue());
        TimeUnit.SECONDS.sleep(value);
    }
}
