package executor.service.facade.execution;

import executor.service.annotation.Autowired;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.service.executor.ScenarioExecutor;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class ExecutionServiceImpl implements ExecutionService {

    private final Logger log = Logger.getLogger("ParallelFlowExecutorServiceImpl");
    private final BlockingQueue<Scenario> scenarioQueue;
    private final ScenarioExecutor scenarioExecutor;

    @Autowired
    public ExecutionServiceImpl(ScenarioQueueHolder scenarioQueueHolder, ScenarioExecutor scenarioExecutor) {
        this.scenarioQueue = scenarioQueueHolder.getQueue();
        this.scenarioExecutor = scenarioExecutor;
    }

    @Override
    public void execute(Supplier<WebDriver> webDriverSupplier) {
        while (!Thread.currentThread().isInterrupted()) {
            log.info(Thread.currentThread().getName() + " | scenarios in queue: " + scenarioQueue.size()); // todo: add logger
            Scenario scenario = getScenario();
            scenarioExecutor.execute(scenario, webDriverSupplier.get());
        }
    }

    private Scenario getScenario() {
        try {
            synchronized (scenarioQueue) {
                if (scenarioQueue.isEmpty()) {
                    log.info("Scenario queue is empty!");
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
