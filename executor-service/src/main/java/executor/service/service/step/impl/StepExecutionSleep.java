package executor.service.service.step.impl;

import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
                Thread.sleep(duration * 1000L);
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
