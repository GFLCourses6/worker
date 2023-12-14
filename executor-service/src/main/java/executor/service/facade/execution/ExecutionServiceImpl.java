package executor.service.facade.execution;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.service.executor.ScenarioExecutor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class ExecutionServiceImpl implements ExecutionService {

    private final Logger logger = LogManager.getLogger(ExecutionServiceImpl.class);

    private final BlockingQueue<Scenario> scenarioQueue;
    private final ScenarioExecutor scenarioExecutor;

    public ExecutionServiceImpl(ScenarioQueueHolder scenarioQueueHolder, ScenarioExecutor scenarioExecutor) {
        this.scenarioQueue = scenarioQueueHolder.getQueue();
        this.scenarioExecutor = scenarioExecutor;
    }

    @Override
    public void execute(Supplier<WebDriver> webDriverSupplier) {
        while (!Thread.currentThread().isInterrupted()) {
            logger.info("Scenarios in queue: {}", scenarioQueue.size());
            Scenario scenario = getScenario();
            logger.info("Executing the scenario '{}'", scenario.getName());
            scenarioExecutor.execute(scenario, webDriverSupplier.get());
        }
    }

    private Scenario getScenario() {
        try {
            synchronized (scenarioQueue) {
                if (scenarioQueue.isEmpty()) {
                    logger.info("ScenarioQueue is empty!");
                    scenarioQueue.notify();
                }
            }
            return scenarioQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
