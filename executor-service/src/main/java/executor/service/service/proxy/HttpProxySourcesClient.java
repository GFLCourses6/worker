package executor.service.service.proxy;

import executor.service.model.dto.ProxyConfigHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpProxySourcesClient implements ProxySourcesClient {

    @Value("${client.url.proxy}")
    private String clientUrl;
    private final Logger logger = LoggerFactory.getLogger(HttpProxySourcesClient.class);
    private final RestTemplate restTemplate;

    public HttpProxySourcesClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ProxyConfigHolder getProxy(String username) {
        String url = clientUrl + "/" + username;
        try {
            return getProxyConfigHolder(url);
        } catch (Exception e) {
            logger.error("Couldn't retrieve proxy for {}.\nMore: {}",
                    username, e.getMessage());
            return null;
        }
    }

    private ProxyConfigHolder getProxyConfigHolder(String url) {
        ResponseEntity<ProxyConfigHolder> responseEntity =
                restTemplate.getForEntity(url, ProxyConfigHolder.class);

        if (responseEntity.getStatusCode().is2xxSuccessful())
            return responseEntity.getBody();
        return null;
    }
}


