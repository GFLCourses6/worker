package executor.service.facade.parallel;

import executor.service.annotation.Autowired;
import executor.service.facade.execution.ExecutionService;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import executor.service.service.listener.ScenarioSourceListener;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.webDriver.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private final Logger log = Logger.getLogger(ParallelFlowExecutorServiceImpl.class.getName());
    private final ExecutorService configurableThreadPool;
    private final ScenarioSourceListener scenarioSourceListener;
    private final WebDriverInitializer webDriverInitializer;
    private final ProxySourcesClient proxySourcesClient;
    private final ExecutionService executionService;
    private final ThreadPoolConfig threadPoolConfig;
    private final BlockingQueue<Scenario> scenarioQueue;

    @Autowired
    public ParallelFlowExecutorServiceImpl(ScenarioSourceListener scenarioSourceListener,
                                           WebDriverInitializer webDriverInitializer,
                                           ProxySourcesClient proxySourcesClient,
                                           ExecutionService executionService,
                                           ThreadPoolConfig threadPoolConfig,
                                           ExecutorService configurableThreadPool,
                                           ScenarioQueueHolder scenarioQueueHolder) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.webDriverInitializer = webDriverInitializer;
        this.proxySourcesClient = proxySourcesClient;
        this.executionService = executionService;
        this.threadPoolConfig = threadPoolConfig;
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

        for (int i = 0; i < threadPoolConfig.getCorePoolSize() - 1; i++) {
            configurableThreadPool.submit(getWorker());
        }
    }

    private Runnable startScenarioListener() {
        return () -> {
            while (!Thread.currentThread().isInterrupted()) {
                synchronized (scenarioQueue) {
                    if (!scenarioQueue.isEmpty()) {
                        try {
                            log.info("Wait until ScenarioQueue is empty");
                            scenarioQueue.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    log.info("Adding elements to the ScenarioQueue");
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
