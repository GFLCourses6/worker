package executor.service.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import executor.service.holder.ProxyCredentialsHolder;
import executor.service.holder.ProxyNetworkConfigHolder;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.params.ProxyConfigHolderArgumentsProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

class ProxySourcesClientServiceTest {
    private ProxySourcesClientService proxyService;
    @Mock
    private ObjectMapper mockObjectMapper;
    @Mock
    private ProxyNetworkConfigHolder mockNetworkConfigHolder;
    @Mock
    private ProxyCredentialsHolder mockCredentialsHolder;
    private AutoCloseable closeable;

    @BeforeEach
    void setUp() throws IOException {
        closeable = MockitoAnnotations.openMocks(this);
        TypeFactory typeFactory = new ObjectMapper().getTypeFactory(); // Get a valid TypeFactory
        when(mockObjectMapper.getTypeFactory()).thenReturn(typeFactory);
        proxyService = new ProxySourcesClientService(mockObjectMapper, mockNetworkConfigHolder, mockCredentialsHolder);
    }
    @AfterEach
    public void cleanUp() throws Exception {
        closeable.close();
    }

    @Test
    @DisplayName("Test getProxy() Successfully, mocking behavior for network config and credentials holders")
    void testGetProxySuccessfully() {

        ProxyNetworkConfig mockNetworkConfig = new ProxyNetworkConfig();
        ProxyCredentials mockCredentials = new ProxyCredentials();
        when(mockNetworkConfigHolder.poll()).thenReturn(Optional.of(mockNetworkConfig));
        when(mockCredentialsHolder.poll()).thenReturn(Optional.of(mockCredentials));

        ProxyConfigHolder result = proxyService.getProxy();

        assertSame(mockNetworkConfig, result.getProxyNetworkConfig());
        assertSame(mockCredentials, result.getProxyCredentials());
        verify(mockNetworkConfigHolder, times(1)).poll();
        verify(mockCredentialsHolder, times(1)).poll();
    }

    @Test
    @DisplayName("Test getProxy() Failed - Throws NoSuchElementException, simulating an empty holder by making poll() return null")
    void testGetProxyFailedThrowsNoSuchElementException() {
        when(mockNetworkConfigHolder.poll()).thenReturn(Optional.empty());
        when(mockCredentialsHolder.poll()).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()-> proxyService.getProxy());
    }

    @ParameterizedTest
    @ArgumentsSource(ProxyConfigHolderArgumentsProvider.class)
    @DisplayName("Test getProxies() with provided ProxyConfigHolders")
    void testGetProxies(Queue<ProxyConfigHolder> proxyConfigHolderQueue) {
        for (ProxyConfigHolder proxyConfigHolder : proxyConfigHolderQueue) {
            ProxyNetworkConfig mockNetworkConfig = new ProxyNetworkConfig();
            ProxyCredentials mockCredentials = new ProxyCredentials();
            when(mockNetworkConfigHolder.poll()).thenReturn(Optional.of(mockNetworkConfig));
            when(mockCredentialsHolder.poll()).thenReturn(Optional.of(mockCredentials));

            ProxyConfigHolder result = proxyService.getProxy();

            assertSame(mockNetworkConfig, result.getProxyNetworkConfig());
            assertSame(mockCredentials, result.getProxyCredentials());
        }
        verify(mockNetworkConfigHolder, times(2)).poll();
        verify(mockCredentialsHolder, times(2)).poll();
    }
}