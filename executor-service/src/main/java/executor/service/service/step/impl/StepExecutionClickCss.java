package executor.service.service.step.impl;

import executor.service.exception.StepExecutionException;
import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import static executor.service.service.executor.Action.CLICK_CSS_ACTION;

public class StepExecutionClickCss implements StepExecution {
    @Override
    public String getStepAction() {
        return CLICK_CSS_ACTION;
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String cssSelector = step.getValue();
        try {
            webDriver.findElement(By.cssSelector(cssSelector)).click();
        } catch (WebDriverException e) {
            throw new StepExecutionException(
                    "An error occurred while clicking the element", e);
        }
    }
}
