package executor.service.facade.execution;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.Scenario;
import executor.service.model.entity.ScenarioResult;
import executor.service.service.executor.ScenarioExecutor;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.scenario.result.ScenarioResultService;
import executor.service.service.webDriver.WebDriverInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class ScenarioWorker implements ExecutionService {

    private final Logger logger = LogManager.getLogger(ScenarioWorker.class);

    private final BlockingQueue<Scenario> scenarioQueue;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriverInitializer webDriverInitializer;
    private final ProxySourcesClient proxySourcesClient;
    private final ScenarioResultService scenarioResultService;

    public ScenarioWorker(ScenarioQueueHolder scenarioQueueHolder,
                          ScenarioExecutor scenarioExecutor,
                          WebDriverInitializer webDriverInitializer,
                          ProxySourcesClient proxySourcesClient,
                          ScenarioResultService scenarioResultService) {
        this.scenarioQueue = scenarioQueueHolder.getQueue();
        this.scenarioExecutor = scenarioExecutor;
        this.webDriverInitializer = webDriverInitializer;
        this.proxySourcesClient = proxySourcesClient;
        this.scenarioResultService = scenarioResultService;
    }

    @Override
    public void execute() {
        while (!Thread.currentThread().isInterrupted()) {
            logger.info("Scenarios in queue: {}", scenarioQueue.size());
            Scenario scenario = getScenario();
            if (scenario != null) {
                execute(scenario);
            }
        }
    }

    private Scenario getScenario() {
        try {
            return scenarioQueue.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    private void execute(Scenario scenario) {
        logger.info("Executing the scenario '{}'", scenario.getName());
        String username = scenario.getUsername();
        ProxyConfigHolder proxy = proxySourcesClient.getProxy(username);
        WebDriver driver = webDriverInitializer.create(proxy);
        ScenarioResult result = scenarioExecutor.execute(scenario, driver);
        result.setProxy(proxy);
        scenarioResultService.createScenarioResult(result);
    }
}
