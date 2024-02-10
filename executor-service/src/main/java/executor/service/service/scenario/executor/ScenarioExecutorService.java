package executor.service.service.scenario.executor;

import executor.service.model.dto.Scenario;
import executor.service.model.dto.Step;
import executor.service.model.entity.ScenarioResult;
import executor.service.model.entity.StepResult;
import executor.service.service.step.executor.StepExecutor;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

@Service
public class ScenarioExecutorService implements ScenarioExecutor {

    private final Logger logger = LoggerFactory.getLogger(ScenarioExecutorService.class);

    @Autowired
    private StepExecutor stepExecutor;

    @Override
    public ScenarioResult execute(Scenario scenario, WebDriver webDriver) {
        try {
            webDriver.get(scenario.getSite());
            return executeSteps(scenario, webDriver);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return new ScenarioResult(scenario, e.getMessage());
        }
        finally {
            webDriver.quit();
        }
    }

    private ScenarioResult executeSteps(Scenario scenario, WebDriver driver) {
        Queue<Step> steps = new ArrayDeque<>(scenario.getSteps());
        ScenarioResult scenarioResult = new ScenarioResult(scenario);

        while (!steps.isEmpty()) {
            Step step = steps.poll();
            StepResult result = stepExecutor.makeStep(driver, step);
            scenarioResult.addStepResult(result);
        }
        return scenarioResult;
    }
}
