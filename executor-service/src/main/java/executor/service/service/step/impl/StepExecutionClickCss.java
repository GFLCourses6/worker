package executor.service.service.step.impl;

import executor.service.model.dto.Step;
import executor.service.service.step.AbstractStepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static executor.service.service.scenario.executor.Action.CLICK_CSS_ACTION;

public class StepExecutionClickCss extends AbstractStepExecution {

    @Override
    public String getStepAction() {
        return CLICK_CSS_ACTION;
    }

    @Override
    protected void executeStepLogic(WebDriver webDriver, Step step) {
        String cssSelector = step.getValue();
        webDriver.findElement(By.cssSelector(cssSelector)).click();
    }
}
