package executor.service.service.step.impl;

import executor.service.model.Step;
import executor.service.model.StepResult;
import executor.service.service.step.AbstractStepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static executor.service.service.executor.Action.CLICK_XPATH_ACTION;

public class StepExecutionClickXpath extends AbstractStepExecution {

    @Override
    public String getStepAction() {
        return CLICK_XPATH_ACTION;
    }

    @Override
    public StepResult step(WebDriver webDriver, Step step) {
        String xpath = step.getValue();
        return computeStepResult(step, () ->
                webDriver.findElement(By.xpath(xpath)).click()
        );
    }
}