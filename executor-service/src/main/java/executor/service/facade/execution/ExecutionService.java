package executor.service.facade.execution;

import executor.service.service.executor.ScenarioExecutor;
import org.openqa.selenium.WebDriver;

public interface ExecutionService {

    void execute(WebDriver webDriver, ScenarioExecutor scenarioExecutor);
}
