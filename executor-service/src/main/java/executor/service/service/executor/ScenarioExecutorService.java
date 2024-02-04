package executor.service.service.executor;

import executor.service.model.dto.Scenario;
import executor.service.model.dto.Step;
import executor.service.model.entity.ExecutionStatus;
import executor.service.model.entity.ScenarioResult;
import executor.service.model.entity.StepResult;
import executor.service.service.step.StepExecution;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Queue;

import static executor.service.service.step.impl.StepExecutionType.fromString;

@Service
public class ScenarioExecutorService implements ScenarioExecutor {

    private final Logger logger = LoggerFactory.getLogger(ScenarioExecutorService.class);

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
            StepResult result = makeStep(driver, step);
            scenarioResult.addStepResult(result);
        }
        return scenarioResult;
    }

    private StepResult makeStep(WebDriver driver, Step step) {
        try {
            return getStepExecution(step).step(driver, step);
        } catch (Exception e) {
            return new StepResult(step, e.getMessage(), ExecutionStatus.FAIL);
        }
    }

    private StepExecution getStepExecution(Step step) {
        return fromString(step.getAction())
                .getStepExecution();
    }
}
