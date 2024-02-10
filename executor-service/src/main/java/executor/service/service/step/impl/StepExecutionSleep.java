package executor.service.service.step.impl;

import executor.service.model.dto.Step;
import executor.service.service.step.AbstractStepExecution;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static executor.service.service.scenario.executor.Action.SLEEP_ACTION;

@Component
public class StepExecutionSleep extends AbstractStepExecution {

    @Override
    public String getStepAction() {
        return SLEEP_ACTION;
    }

    @Override
    protected void executeStepLogic(WebDriver webDriver, Step step) throws Exception {
        long value = Long.parseLong(step.getValue());
        if (value > 30)
            throw new IllegalArgumentException("'sleep' value can't be more than 30s.");
        TimeUnit.SECONDS.sleep(value);
    }
}
