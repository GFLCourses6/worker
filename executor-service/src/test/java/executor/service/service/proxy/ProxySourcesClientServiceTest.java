package executor.service.service.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.util.file.FileParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;
import java.util.concurrent.BlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ProxySourcesClientServiceTest {

    @Mock
    private FileParser fileParser;
    @Mock
    private BlockingQueue<ProxyConfigHolder> mockProxyConfigQueue;
    @InjectMocks
    private ProxySourcesClientService proxySourcesClient;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        closeable = MockitoAnnotations.openMocks(this);
        // mocking private queue
        Field proxyConfigQueueField = ReflectionUtils.findFields(ProxySourcesClientService.class,
                        f -> f.getName().equals("proxyConfigHolders"), ReflectionUtils.HierarchyTraversalMode.TOP_DOWN
                ).get(0);
        proxyConfigQueueField.setAccessible(true);
        proxyConfigQueueField.set(proxySourcesClient, mockProxyConfigQueue);
    }

    @AfterEach
    void cleanUp() throws Exception {
        closeable.close();
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.ProxySourcesClientArgs#getProxy")
    @DisplayName("Test - the positive scenario for the `getProxy` method, when" +
            "there's ProxyNetworkConfig with/without credentials")
    void getProxyTestPositive(ProxyConfigHolder expected) {
        when(mockProxyConfigQueue.peek()).thenReturn(expected);
        when(mockProxyConfigQueue.poll()).thenReturn(expected);

        ProxyConfigHolder actual = proxySourcesClient.getProxy();
        verify(mockProxyConfigQueue, never()).addAll(any());
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - the scenario for the `getProxy` method, when " +
            "there's no ProxyNetworkConfig in the queue")
    void verifyRefillsQueueWhenPeekedNull() {
        when(mockProxyConfigQueue.peek()).thenReturn(null);

        proxySourcesClient.getProxy();

        verify(mockProxyConfigQueue, times(1)).addAll(any());
    }
}