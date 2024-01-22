package executor.service.service.proxy;

import executor.service.model.dto.ProxyConfigHolder;
import executor.service.security.RsaManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpProxySourcesClient implements ProxySourcesClient {

    private RsaManager rsaManager;
    @Value("${client.url.proxy}")
    private String clientProxyUrl;
    @Value("${executor.service.auth.token.value}")
    private String workerApiToken;

    private final Logger logger = LoggerFactory.getLogger(HttpProxySourcesClient.class);
    private final RestTemplate restTemplate;

    public HttpProxySourcesClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProxyConfigHolder getProxy(String username) {
        try {
            String url = clientProxyUrl + "/" + username;
            return getProxyConfigHolder(url);
        } catch (Exception e) {
            logger.error(
                    "Couldn't retrieve proxy for {}. {}",
                    username, e.getMessage()
            );
            return null;
        }
    }

    private ProxyConfigHolder getProxyConfigHolder(String url) {
        var requestEntity = getHttpEntity();
        var responseEntity = restTemplate.exchange(
                url, HttpMethod.GET,
                requestEntity, ProxyConfigHolder.class
        );
        if (responseEntity.getStatusCode().is2xxSuccessful())
            return responseEntity.getBody();
        return null;
    }

    private HttpEntity<Object> getHttpEntity() {
        var headers = new HttpHeaders();

        rsaManager.initFromStrings();
        String encryptedWorkerApiToken = rsaManager.encrypt(workerApiToken);
        headers.set(HttpHeaders.AUTHORIZATION, "Token " + encryptedWorkerApiToken);
        return new HttpEntity<>(headers);
    }
}


