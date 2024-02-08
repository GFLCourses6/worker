package executor.service.facade.execution;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.Scenario;
import executor.service.model.entity.ScenarioResult;
import executor.service.service.scenario.executor.ScenarioExecutor;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.scenario.result.ScenarioResultService;
import executor.service.service.webDriver.WebDriverInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.BlockingQueue;

import static org.mockito.Mockito.*;

class ExecutionServiceImplTest {
    @Mock
    private ScenarioExecutor scenarioExecutor;
    private ScenarioQueueHolder scenarioQueueHolder;
    private WebDriverInitializer webDriverInitializer;
    private ProxySourcesClient proxySourcesClient;
    private ExecutionService executionService;
    private BlockingQueue<Scenario> scenarioQueue;
    private ScenarioResultService scenarioResultService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        webDriverInitializer = mock(WebDriverInitializer.class);
        proxySourcesClient = mock(ProxySourcesClient.class);
        scenarioQueueHolder = new ScenarioQueueHolder();
        scenarioQueue = scenarioQueueHolder.getQueue();
        scenarioResultService = mock(ScenarioResultService.class);
        executionService = new ScenarioWorker(
                scenarioQueueHolder,
                scenarioExecutor,
                webDriverInitializer,
                proxySourcesClient,
                scenarioResultService);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Test execute method")
    void execute() throws InterruptedException {
        WebDriver mockWebDriver = mock(WebDriver.class);
        ProxyConfigHolder proxyConfigHolder = mock(ProxyConfigHolder.class);
        when(webDriverInitializer.create(any())).thenReturn(mockWebDriver);
        when(proxySourcesClient.getProxy(anyString())).thenReturn(proxyConfigHolder);
        when(scenarioExecutor.execute(any(), any())).thenReturn(mock(ScenarioResult.class));
        Scenario scenario1 = new Scenario("TestScenario1", "example1.com", "", null);
        Scenario scenario2 = new Scenario("TestScenario2", "example2.com", "", null);

        scenarioQueue.add(scenario1);
        scenarioQueue.add(scenario2);

        Thread executionThread = new Thread(() -> executionService.execute());
        executionThread.start();
        Thread.sleep(1000);

        verify(scenarioExecutor, times(1)).execute(scenario1, mockWebDriver);
        verify(scenarioExecutor, times(1)).execute(scenario2, mockWebDriver);
        verify(scenarioResultService, times(2)).createScenarioResult(any(ScenarioResult.class));
    }
}