package executor.service.service.Impl;

import executor.service.model.Step;
import executor.service.service.StepExecutionClickCss;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickCssImpl implements StepExecutionClickCss {
    @Override
    public String getStepAction() {
        return "clickCss";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String cssSelector = step.getAction();
        WebElement webElement = webDriver.findElement(By.cssSelector(cssSelector));
        webElement.click();
    }
}
