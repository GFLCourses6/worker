package executor.service.service.step;

import executor.service.model.Step;
import executor.service.model.StepResult;
import org.openqa.selenium.WebDriver;

public interface StepExecution {
    String getStepAction();
    StepResult step(WebDriver webDriver, Step step);
}
