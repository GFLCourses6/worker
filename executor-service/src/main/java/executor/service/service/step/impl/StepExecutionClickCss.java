package executor.service.service.step.impl;

import executor.service.model.Step;
import executor.service.model.StepResult;
import executor.service.service.step.AbstractStepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static executor.service.service.executor.Action.CLICK_CSS_ACTION;

public class StepExecutionClickCss extends AbstractStepExecution {

    @Override
    public String getStepAction() {
        return CLICK_CSS_ACTION;
    }

    @Override
    public StepResult step(WebDriver webDriver, Step step) {
        String cssSelector = step.getValue();
        return computeStepResult(step, () ->
                webDriver.findElement(By.cssSelector(cssSelector)).click()
        );
    }
}
