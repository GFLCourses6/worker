package executor.service.service.step.impl;

import executor.service.exception.StepExecutionInterruptedException;
import executor.service.exception.StepNumberFormatException;
import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

import static executor.service.service.executor.Action.SLEEP_ACTION;
import static java.lang.StrictMath.random;
import static java.lang.StrictMath.round;

public class StepExecutionSleep implements StepExecution {
    @Override
    public String getStepAction() {
        return SLEEP_ACTION;
    }

    @Override
    public void step(WebDriver webDriver, Step step) {

        String value = step.getValue();
        try {
            String[] timeParts = value.split(":");
            long a = Long.parseLong(timeParts[0]);
            long b = Long.parseLong(timeParts[1]);
            TimeUnit.SECONDS.sleep(round(a + (random() * ((b - a) + 1))));
        } catch (NumberFormatException e) {
            throw new StepNumberFormatException(
                    "Invalid step duration value:", value);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new StepExecutionInterruptedException(
                    "Step execution interrupted while sleeping", e);
        }
    }
}
