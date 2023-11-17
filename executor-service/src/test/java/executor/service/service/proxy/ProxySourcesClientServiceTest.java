package executor.service.service.proxy;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
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
import java.util.NoSuchElementException;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProxySourcesClientServiceTest {

    @Mock
    private FileParser fileParser;
    @Mock
    private Queue<ProxyNetworkConfig> mockNetworkConfigQueue;
    @Mock
    private Queue<ProxyCredentials> mockCredentialsQueue;
    @InjectMocks
    private ProxySourcesClientService proxySourcesClient;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws IllegalAccessException {
        closeable = MockitoAnnotations.openMocks(this);
        // mocking private queues
        Field networkConfigQueueField = ReflectionUtils.findFields(ProxySourcesClientService.class,
                        f -> f.getName().equals("networkConfigQueue"), ReflectionUtils.HierarchyTraversalMode.TOP_DOWN
                ).get(0);
        Field credentialsQueueField = ReflectionUtils.findFields(ProxySourcesClientService.class,
                        f -> f.getName().equals("credentialsQueue"), ReflectionUtils.HierarchyTraversalMode.TOP_DOWN
                ).get(0);
        credentialsQueueField.setAccessible(true);
        networkConfigQueueField.setAccessible(true);
        credentialsQueueField.set(proxySourcesClient, mockCredentialsQueue);
        networkConfigQueueField.set(proxySourcesClient, mockNetworkConfigQueue);
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
        when(mockCredentialsQueue.poll()).thenReturn(expected.getProxyCredentials());
        when(mockNetworkConfigQueue.poll()).thenReturn(expected.getProxyNetworkConfig());

        ProxyConfigHolder actual = proxySourcesClient.getProxy();
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Test - the negative scenario for the `getProxy` method, when " +
            "there's no ProxyNetworkConfig in the queue")
    void getProxyTestNegative() {
        when(mockNetworkConfigQueue.poll()).thenReturn(null);
        assertThrows(NoSuchElementException.class, () -> proxySourcesClient.getProxy());
    }
}