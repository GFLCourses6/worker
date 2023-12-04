package executor.service.service.executor;

import executor.service.model.Scenario;
import executor.service.model.ScenarioLogMessage;
import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import java.util.ArrayDeque;
import java.util.Queue;

import static executor.service.service.executor.StepExecutionType.fromString;

public class ScenarioExecutorService
        implements ScenarioExecutor {

    private final Logger logger = LogManager.getLogger(ScenarioExecutorService.class);

    @Override
    public void execute(
            final Scenario scenario,
            final WebDriver webDriver) {
        webDriver.get(scenario.getSite());
        executeSteps(scenario, webDriver);
        webDriver.quit();
    }

    private void executeSteps(
            final Scenario scenario,
            final WebDriver driver) {
        Queue<Step> steps = new ArrayDeque<>(scenario.getSteps());
        ScenarioLogMessage message = new ScenarioLogMessage(scenario.getName());

        while (!steps.isEmpty()) {
            Step step = steps.poll();
            try {
                getStepExecution(step).step(driver, step);
            } catch (Exception e) {
                message.addExecutionMessage(e.getMessage());
                logger.error(message);
                return;
            }
        }
        message.addExecutionMessage("Scenario is successfully executed");
        logger.info(message);
    }

    private StepExecution getStepExecution(
            final Step step) {
        return fromString(step.getAction())
                .getStepExecution();
    }
}
