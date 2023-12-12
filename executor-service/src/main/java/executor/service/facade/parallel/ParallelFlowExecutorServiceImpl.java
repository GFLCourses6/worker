package executor.service.facade.parallel;

import executor.service.facade.execution.ExecutionService;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.service.listener.ScenarioSourceListener;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.webDriver.WebDriverInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import org.springframework.stereotype.Service;
import executor.service.annotation.Autowired;

@Service
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private final Logger logger = LogManager.getLogger(ParallelFlowExecutorServiceImpl.class);
    private final ThreadPoolExecutor configurableThreadPool;
    private final ScenarioSourceListener scenarioSourceListener;
    private final WebDriverInitializer webDriverInitializer;
    private final ProxySourcesClient proxySourcesClient;
    private final ExecutionService executionService;
    private final BlockingQueue<Scenario> scenarioQueue;

    @Autowired
    public ParallelFlowExecutorServiceImpl(ScenarioSourceListener scenarioSourceListener,
                                           WebDriverInitializer webDriverInitializer,
                                           ProxySourcesClient proxySourcesClient,
                                           ExecutionService executionService,
                                           ThreadPoolExecutor configurableThreadPool,
                                           ScenarioQueueHolder scenarioQueueHolder) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.webDriverInitializer = webDriverInitializer;
        this.proxySourcesClient = proxySourcesClient;
        this.executionService = executionService;
        this.configurableThreadPool = configurableThreadPool;
        this.scenarioQueue = scenarioQueueHolder.getQueue();
    }

    @Override
    public void execute() {
        if (!configurableThreadPool.isTerminated()) {
            submitTasksToExecutor();
        }
    }

    private void submitTasksToExecutor() {
        configurableThreadPool.submit(startScenarioListener());

        for (int i = 0; i < configurableThreadPool.getCorePoolSize() - 1; i++) {
            configurableThreadPool.submit(getWorker());
        }
    }

    private Runnable startScenarioListener() {
        return () -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (scenarioQueue) {
                    if (!scenarioQueue.isEmpty()) {
                        try {
                            logger.info("Wait until ScenarioQueue is empty");
                            scenarioQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    logger.info("Adding elements to the ScenarioQueue");
                    scenarioSourceListener.execute();
                }
            }
        };
    }

    private Runnable getWorker() {
        return () -> executionService.execute(this::initializeWebDriver);
    }

    private WebDriver initializeWebDriver() {
        ProxyConfigHolder proxyConfigHolder = proxySourcesClient.getProxy();
        return webDriverInitializer.create(proxyConfigHolder);
    }
}
