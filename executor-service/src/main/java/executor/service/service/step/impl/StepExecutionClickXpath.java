package executor.service.service.step.impl;

import executor.service.model.dto.Step;
import executor.service.service.step.AbstractStepExecution;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

import static executor.service.service.scenario.executor.Action.CLICK_XPATH_ACTION;

@Component
public class StepExecutionClickXpath extends AbstractStepExecution {

    @Override
    public String getStepAction() {
        return CLICK_XPATH_ACTION;
    }

    @Override
    protected void executeStepLogic(WebDriver webDriver, Step step) {
        String xpath = step.getValue();
        webDriver.findElement(By.xpath(xpath)).click();
    }
}