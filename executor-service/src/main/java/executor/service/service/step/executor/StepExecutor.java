package executor.service.service.step.executor;

import executor.service.model.dto.Step;
import executor.service.model.entity.StepResult;
import org.openqa.selenium.WebDriver;

public interface StepExecutor {

    StepResult makeStep(WebDriver driver, Step step);
}
