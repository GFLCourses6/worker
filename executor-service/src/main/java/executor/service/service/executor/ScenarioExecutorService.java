package executor.service.service.executor;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.WebDriver;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static executor.service.service.executor.StepExecutionType.fromString;

public class ScenarioExecutorService
        implements ScenarioExecutor {
    @Override
    public void execute(
            final Scenario scenario,
            final WebDriver webDriver) {
        webDriver.get(scenario.getSite());
        executeSteps(new LinkedBlockingQueue<>(scenario.getSteps()), webDriver);
        webDriver.quit();
    }

    private void executeSteps(
            final Queue<Step> steps,
            final WebDriver driver) {
        while (!steps.isEmpty()) {
            Step step = steps.poll();
            getStepExecution(step).step(driver, step);
        }
    }

    private StepExecution getStepExecution(
            final Step step) {
        return fromString(step.getAction())
                .getStepExecution();
    }
}
