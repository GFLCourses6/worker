package executor.service.service.step.impl;

import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;

public class StepExecutionSleep implements StepExecution {
    @Override
    public String getStepAction() {
        return "Sleep";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String value = step.getValue();

        try {
            int duration = Integer.parseInt(value);

            if (duration > 0) {
                TimeUnit.SECONDS.sleep(duration);
            } else {
                System.out.println("Step duration is not specified, skipping.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid step duration value: " + value);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}