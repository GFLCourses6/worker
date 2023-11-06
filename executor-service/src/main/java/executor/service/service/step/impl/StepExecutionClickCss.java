package executor.service.service.step.impl;

import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickCss implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String cssSelector = step.getValue();
        WebElement webElement = webDriver.findElement(By.cssSelector(cssSelector));
        webElement.click();
    }
}
