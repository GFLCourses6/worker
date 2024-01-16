package executor.service.service.proxy;

import executor.service.model.dto.ProxyConfigHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
class HttpProxySourcesClientTest {

    @Value("${client.url.proxy}")
    private String clientProxyUrl;

    @Autowired
    private HttpProxySourcesClient proxySourcesClient;

    @MockBean
    private RestTemplate restTemplate;

    @Mock
    private ResponseEntity<ProxyConfigHolder> responseEntity;

    @BeforeEach
    void setUp() {
        when(restTemplate.getForEntity(anyString(), eq(ProxyConfigHolder.class)))
                .thenReturn(responseEntity);
    }

    @Test
    void testSuccess200ProxyRetrieving() {
        var expected = new ProxyConfigHolder();
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.OK);
        when(responseEntity.getBody()).thenReturn(expected);

        var username = "dobby";
        var actual = proxySourcesClient.getProxy(username);

        verify(restTemplate).getForEntity(clientProxyUrl + "/" + username, ProxyConfigHolder.class);
        assertEquals(expected, actual);
    }

    @Test
    void testFail400ProxyRetrieving() {
        when(responseEntity.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
        when(responseEntity.getBody()).thenReturn(null);

        var username = "dobby";
        var actual = proxySourcesClient.getProxy(username);

        verify(restTemplate).getForEntity(clientProxyUrl + "/" + username, ProxyConfigHolder.class);
        assertNull(actual);
    }

    @Test
    void testFailExceptionProxyRetrieving() {
        when(restTemplate.getForEntity(anyString(), eq(ProxyConfigHolder.class)))
                .thenThrow(new RuntimeException());

        var username = "dobby";
        var actual = proxySourcesClient.getProxy(username);

        verify(restTemplate).getForEntity(clientProxyUrl + "/" + username, ProxyConfigHolder.class);
        assertNull(actual);
    }
}
