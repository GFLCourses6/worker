package executor.service.facade.execution;

import executor.service.annotation.Autowired;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.service.executor.ScenarioExecutor;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;

public class ExecutionServiceImpl implements ExecutionService {

    private final BlockingQueue<Scenario> scenarioQueue;

    @Autowired
    public ExecutionServiceImpl(ScenarioQueueHolder scenarioQueueHolder) {
        this.scenarioQueue = scenarioQueueHolder.getQueue();
    }

    @Override
    public void execute(WebDriver webDriver, ScenarioExecutor scenarioExecutor) {
        scenarioExecutor.execute(getScenario(), webDriver);
    }

    private Scenario getScenario() {
        try {
            return scenarioQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }
}
