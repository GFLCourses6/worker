package executor.service.service.scenario.executor;

import executor.service.model.dto.Scenario;
import executor.service.model.entity.ScenarioResult;
import org.openqa.selenium.WebDriver;

public interface ScenarioExecutor {

    ScenarioResult execute(
            Scenario scenario,
            WebDriver webDriver);
}
