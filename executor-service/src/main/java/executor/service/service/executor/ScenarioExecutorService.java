package executor.service.service.executor;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.model.entity.ScenarioResult;
import executor.service.model.entity.StepResult;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

import static executor.service.service.step.impl.StepExecutionType.fromString;

@Service
public class ScenarioExecutorService
        implements ScenarioExecutor {

    @Override
    public ScenarioResult execute(
            final Scenario scenario,
            final WebDriver webDriver) {
        webDriver.get(scenario.getSite());
        return executeSteps(scenario, webDriver);
    }

    private ScenarioResult executeSteps(
            final Scenario scenario,
            final WebDriver driver) {
        Queue<Step> steps = new ArrayDeque<>(scenario.getSteps());
        var scenarioResult = new ScenarioResult(scenario);
        while (!steps.isEmpty()) {
            Step step = steps.poll();
            StepResult stepResult = getStepExecution(step).step(driver, step);
            scenarioResult.addStepResult(stepResult);
        }
        driver.quit();
        return scenarioResult;
    }

    private StepExecution getStepExecution(
            final Step step) {
        return fromString(step.getAction())
                .getStepExecution();
    }
}
