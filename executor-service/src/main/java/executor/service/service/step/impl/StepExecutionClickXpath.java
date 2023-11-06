package executor.service.service.step.impl;

import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class StepExecutionClickXpath implements StepExecution {
    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String xpath = step.getValue();
        try {
            WebElement element = webDriver.findElement(By.xpath(xpath));
            element.click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.err.println("Element not found using XPath: " + xpath);
        }
    }
}
