package executor.service.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.holder.ProxyCredentialsHolder;
import executor.service.holder.ProxyNetworkConfigHolder;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public class ProxySourcesClientService implements ProxySourcesClient {
    private static final String PROXY_CREDENTIALS_PATH = "src/json/ProxyCredentials.json";
    private static final String PROXY_NETWORK_CONFIG_PATH = "src/json/ProxyNetworkConfig.json";
    private final ObjectMapper objectMapper;
    private final ProxyNetworkConfigHolder networkConfigHolder;
    private final ProxyCredentialsHolder credentialsHolder;

    public ProxySourcesClientService(ObjectMapper objectMapper, ProxyNetworkConfigHolder networkConfigHolder,
                                      ProxyCredentialsHolder credentialsHolder) throws IOException {
        this.objectMapper = objectMapper;
        this.networkConfigHolder = networkConfigHolder;
        this.credentialsHolder = credentialsHolder;
        getConfigs();
        getCredentials();
    }

    private <T> List<T> getProxyInfoFromFile(String path, Class<T> type) throws IOException {
        return objectMapper.readValue(
                new File(path),
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, type));
    }

    private void getConfigs() throws IOException {
        List<ProxyNetworkConfig> proxyNetworkConfigs = getProxyInfoFromFile(PROXY_NETWORK_CONFIG_PATH, ProxyNetworkConfig.class);
        networkConfigHolder.addAll(proxyNetworkConfigs);
    }

    private void getCredentials() throws IOException {
        List<ProxyCredentials> proxyCredentials = getProxyInfoFromFile(PROXY_CREDENTIALS_PATH, ProxyCredentials.class);
        credentialsHolder.addAll(proxyCredentials);
    }

    @Override
    public ProxyConfigHolder getProxy() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        try {
            proxyConfigHolder.setProxyNetworkConfig(networkConfigHolder.poll().get());
            proxyConfigHolder.setProxyCredentials(credentialsHolder.poll().get());
            return proxyConfigHolder;
        } catch (NoSuchElementException e){
            throw new NoSuchElementException("ProxyNetworkConfig is null");
        }
    }
}


