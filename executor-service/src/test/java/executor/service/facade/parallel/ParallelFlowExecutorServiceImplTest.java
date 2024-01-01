package executor.service.facade.parallel;

import executor.service.facade.execution.ExecutionService;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import executor.service.service.proxy.ProxySourcesClient;
import executor.service.service.webDriver.WebDriverInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ParallelFlowExecutorServiceImplTest {

    private ConfigurableThreadPool configurableThreadPool;
    @Mock
    private WebDriverInitializer webDriverInitializer;
    @Mock
    private ProxySourcesClient proxySourcesClient;
    @Mock
    private ExecutionService executionService;
    @Mock
    private ScenarioQueueHolder scenarioQueueHolder;
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;
    private BlockingQueue<Scenario> scenarioQueue;
    private AutoCloseable autoCloseable;
    private static final int CORE_POOL_SIZE = 10;

    @BeforeEach
    public void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        scenarioQueue = new LinkedBlockingQueue<>();
        when(scenarioQueueHolder.getQueue()).thenReturn(scenarioQueue);
        configurableThreadPool = new ConfigurableThreadPool(new ThreadPoolConfig(CORE_POOL_SIZE, 1000L));
        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(
                executionService, configurableThreadPool);
    }

    @AfterEach
    public void cleanup() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void test() throws InterruptedException {
        scenarioQueue.add(new Scenario());

        parallelFlowExecutorService.execute();

        Thread.sleep(3000);

        assertEquals(CORE_POOL_SIZE, configurableThreadPool.getTaskCount());
        verify(executionService, times(CORE_POOL_SIZE)).execute();
    }
}