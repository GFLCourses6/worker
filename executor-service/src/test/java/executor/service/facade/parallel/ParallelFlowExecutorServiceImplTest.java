package executor.service.facade.parallel;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.facade.execution.ExecutionService;
import executor.service.facade.execution.ExecutionServiceImpl;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.ProxyConfigHolder;
import executor.service.service.executor.ScenarioExecutorService;
import executor.service.service.listener.ScenarioSourceListener;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.proxy.ProxySourcesClientService;
import executor.service.service.webDriver.WebDriverInitializer;
import executor.service.util.file.FileJsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParallelFlowExecutorServiceImplTest {

    private  ThreadPoolExecutor configurableThreadPool;
    private  ScenarioSourceListener scenarioSourceListener;
    private  WebDriverInitializer webDriverInitializer;
    private  ProxySourcesClient proxySourcesClient;
    private  ExecutionService executionService;
    private ScenarioQueueHolder scenarioQueueHolder;

    @BeforeEach
    public void setup(){
        configurableThreadPool = new ThreadPoolExecutor(10, 10,
                60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(10));
        scenarioSourceListener = ()->{};
        webDriverInitializer = proxyConfigHolder -> new ChromeDriver();
        proxySourcesClient = new ProxySourcesClientService(new FileJsonParser(new ObjectMapper()));
        scenarioQueueHolder = new ScenarioQueueHolder();
        executionService = new ExecutionServiceImpl(scenarioQueueHolder, new ScenarioExecutorService());
    }

    @Test
    public void test(){
        ParallelFlowExecutorServiceImpl parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
                scenarioSourceListener, webDriverInitializer, proxySourcesClient,
                executionService, configurableThreadPool, scenarioQueueHolder);


        parallelFlowExecutorService.execute();

        int amountOfSubmittedScenario = 1;
        int amountOfSubmittedWorkers = configurableThreadPool.getCorePoolSize()-1;

        assertEquals(amountOfSubmittedScenario + amountOfSubmittedWorkers,
                configurableThreadPool.getTaskCount());

        assertEquals(amountOfSubmittedScenario + amountOfSubmittedWorkers,
                configurableThreadPool.getActiveCount());


    }

}