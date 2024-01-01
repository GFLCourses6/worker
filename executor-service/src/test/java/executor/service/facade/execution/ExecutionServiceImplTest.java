package executor.service.facade.execution;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.service.executor.ScenarioExecutor;
import executor.service.service.proxy.ProxySourcesClient;
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

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        webDriverInitializer = mock(WebDriverInitializer.class);
        proxySourcesClient = mock(ProxySourcesClient.class);
        scenarioQueueHolder = new ScenarioQueueHolder();
        scenarioQueue = scenarioQueueHolder.getQueue();
        executionService = new ExecutionServiceImpl(
                scenarioQueueHolder,
                scenarioExecutor,
                webDriverInitializer,
                proxySourcesClient);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Test execute method")
    void execute() throws InterruptedException {
        WebDriver mockWebDriver = mock(WebDriver.class);
        when(webDriverInitializer.create(any())).thenReturn(mockWebDriver);
        Scenario scenario1 = new Scenario("TestScenario1", "example1.com", null);
        Scenario scenario2 = new Scenario("TestScenario2", "example2.com", null);

        scenarioQueue.add(scenario1);
        scenarioQueue.add(scenario2);

        Thread executionThread = new Thread(() -> executionService.execute());
        executionThread.start();
        Thread.sleep(1000);

        verify(scenarioExecutor, times(1)).execute(scenario1, mockWebDriver);
        verify(scenarioExecutor, times(1)).execute(scenario2, mockWebDriver);

    }
}