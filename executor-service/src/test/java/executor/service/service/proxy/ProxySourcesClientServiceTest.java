package executor.service.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import executor.service.holder.ProxyCredentialsHolder;
import executor.service.holder.ProxyNetworkConfigHolder;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ProxySourcesClientServiceTest {
    private ProxySourcesClientService proxyService;
    @Mock
    private ObjectMapper mockObjectMapper;
    @Mock
    private ProxyNetworkConfigHolder mockNetworkConfigHolder;
    @Mock
    private ProxyCredentialsHolder mockCredentialsHolder;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);
        TypeFactory typeFactory = new ObjectMapper().getTypeFactory(); // Get a valid TypeFactory
        when(mockObjectMapper.getTypeFactory()).thenReturn(typeFactory);
        proxyService = new ProxySourcesClientService(mockObjectMapper, mockNetworkConfigHolder, mockCredentialsHolder);
    }

    @Test
    void testGetProxySuccessfully() {
        // Mocking behavior for network config and credentials holders
        ProxyNetworkConfig mockNetworkConfig = new ProxyNetworkConfig();
        ProxyCredentials mockCredentials = new ProxyCredentials();
        when(mockNetworkConfigHolder.poll()).thenReturn(Optional.of(mockNetworkConfig));
        when(mockCredentialsHolder.poll()).thenReturn(Optional.of(mockCredentials));

        ProxyConfigHolder result = proxyService.getProxy();

        assertSame(mockNetworkConfig, result.getProxyNetworkConfig());
        assertSame(mockCredentials, result.getProxyCredentials());
    }

    @Test
    void testGetProxyFailedThrowsNoSuchElementException() {
        // Simulating an empty holder by making poll() return null
        when(mockNetworkConfigHolder.poll()).thenReturn(Optional.empty());
        when(mockCredentialsHolder.poll()).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, ()-> proxyService.getProxy());
    }
}