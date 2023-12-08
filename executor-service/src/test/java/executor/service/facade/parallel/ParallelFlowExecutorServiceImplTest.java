package executor.service.facade.parallel;

import executor.service.facade.execution.ExecutionService;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import executor.service.service.listener.ScenarioSourceListener;
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
    private  ScenarioSourceListener scenarioSourceListener;
    @Mock
    private  WebDriverInitializer webDriverInitializer;
    @Mock
    private  ProxySourcesClient proxySourcesClient;
    @Mock
    private  ExecutionService executionService;
    @Mock
    private ScenarioQueueHolder scenarioQueueHolder;
    private ParallelFlowExecutorServiceImpl parallelFlowExecutorService;
    private AutoCloseable autoCloseable;
    private static final int CORE_POOL_SIZE = 10;

    @BeforeEach
    public void setup(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        BlockingQueue<Scenario> scenarioQueue = new LinkedBlockingQueue<>();
        when(scenarioQueueHolder.getQueue()).thenReturn(scenarioQueue);
        configurableThreadPool = new ConfigurableThreadPool(new ThreadPoolConfig(CORE_POOL_SIZE, 1000L));
        parallelFlowExecutorService = new ParallelFlowExecutorServiceImpl(scenarioSourceListener, webDriverInitializer,
                proxySourcesClient, executionService, configurableThreadPool, scenarioQueueHolder);
    }

    @AfterEach
    public void cleanup() throws Exception {
        autoCloseable.close();
    }

    @Test
    public void test() throws InterruptedException {
        doAnswer(call -> {
            scenarioQueueHolder.getQueue().add(new Scenario());
            return null;
        }).when(scenarioSourceListener).execute();

        parallelFlowExecutorService.execute();

        Thread.sleep(3000);

        assertEquals(CORE_POOL_SIZE, configurableThreadPool.getTaskCount());
        verify(executionService, times(CORE_POOL_SIZE-1)).execute(any());
        verify(scenarioSourceListener).execute();
    }

}