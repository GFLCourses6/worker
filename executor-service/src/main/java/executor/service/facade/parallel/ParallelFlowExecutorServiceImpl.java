package executor.service.facade.parallel;

import executor.service.annotation.Autowired;
import executor.service.facade.execution.ExecutionService;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ThreadPoolConfig;
import executor.service.service.ConfigPropertiesLoader;
import executor.service.service.executor.ScenarioExecutor;
import executor.service.service.listener.ScenarioSourceListener;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.webDriver.WebDriverInitializer;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private final BlockingQueue<ProxyConfigHolder> PROXY_QUEUE = new LinkedBlockingQueue<>();
    private final ExecutorService configurableThreadPool = getThreadPoolExecutor();
    private static final String CONFIG_PROPS_PATH = "config.properties";
    private final ScenarioSourceListener scenarioSourceListener;
    private final WebDriverInitializer webDriverInitializer;
    private final ProxySourcesClient proxySourcesClient;
    private final ExecutionService executionService;
    private final ScenarioExecutor scenarioExecutor;

    @Autowired
    public ParallelFlowExecutorServiceImpl(ScenarioSourceListener scenarioSourceListener,
                                           WebDriverInitializer webDriverInitializer,
                                           ProxySourcesClient proxySourcesClient,
                                           ExecutionService executionService,
                                           ScenarioExecutor scenarioExecutor) {
        this.scenarioSourceListener = scenarioSourceListener;
        this.webDriverInitializer = webDriverInitializer;
        this.proxySourcesClient = proxySourcesClient;
        this.executionService = executionService;
        this.scenarioExecutor = scenarioExecutor;
    }

    @Override
    public void execute() {

        while (!configurableThreadPool.isTerminated()) {

            configurableThreadPool.execute(() -> {
                while (!configurableThreadPool.isTerminated()) {
                    submitTasksToExecutor();
                }
            });
        }
    }

    private void submitTasksToExecutor() {

        int numberOfWorkers = 4;

        configurableThreadPool.submit(getScenarioToExecute());
        configurableThreadPool.submit(getProxyToInitializeWebDriver());

        for (int i = 0; i < numberOfWorkers; i++) {
            configurableThreadPool.submit(getWorkers());
        }
    }

    private Runnable getWorkers() {

        return () -> {

            try {
                ProxyConfigHolder proxyToUseForWebDriver;
                proxyToUseForWebDriver = PROXY_QUEUE.take();

                WebDriver webDriver = initializeWebDriver(proxyToUseForWebDriver);

                executionService.execute(webDriver, scenarioExecutor);
                TimeUnit.SECONDS.sleep(4);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    private Runnable getScenarioToExecute() {

        return () -> {
            try {
                try {
                    scenarioSourceListener.execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    private Runnable getProxyToInitializeWebDriver() {

        return () -> {
            ProxyConfigHolder proxy;
            try {
                proxy = proxySourcesClient.getProxy();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                PROXY_QUEUE.put(proxy);
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        };
    }

    private ConfigurableThreadPool getThreadPoolExecutor() {
        ConfigPropertiesLoader configPropertiesLoader = new ConfigPropertiesLoader();
        ThreadPoolConfig threadPoolConfig = threadPoolConfig(configPropertiesLoader);

        return new ConfigurableThreadPool(threadPoolConfig.getCorePoolSize(),
                Integer.MAX_VALUE, threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());

    }

    /**
     * @author Mazurenko Nikita
     */
    private ThreadPoolConfig threadPoolConfig(ConfigPropertiesLoader configPropertiesLoader) {
        Properties properties = configPropertiesLoader.loadConfigProperties(CONFIG_PROPS_PATH);
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

        threadPoolConfig.setCorePoolSize(Integer.parseInt(properties.getProperty("corePoolSize")));
        threadPoolConfig.setKeepAliveTime(Long.parseLong(properties.getProperty("keepAliveTime")));

        return threadPoolConfig;
    }

    private WebDriver initializeWebDriver(ProxyConfigHolder proxyConfigHolder) {
        return webDriverInitializer.create(proxyConfigHolder);
    }

    @Override
    public void shutdown() {
        configurableThreadPool.shutdown();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                configurableThreadPool.awaitTermination(6, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
