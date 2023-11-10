package executor.service.service.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProxySourcesClientService implements ProxySourcesClient {
    private static final String PROXY_CREDENTIALS_PATH = "src/json/ProxyCredentials.json";
    private static final String PROXY_NETWORK_CONFIG_PATH = "src/json/ProxyNetworkConfig.json";
    private final ObjectMapper objectMapper;

    public ProxySourcesClientService(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
    }

    private List<ProxyCredentials> getProxyCredentialsFromFile() throws IOException {
        return objectMapper.readValue(
                new File(PROXY_CREDENTIALS_PATH),
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, ProxyCredentials.class));
    }

    private List<ProxyNetworkConfig> getProxyNetworkConfigFromFile() throws IOException {
        return objectMapper.readValue(
                new File(PROXY_NETWORK_CONFIG_PATH),
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, ProxyNetworkConfig.class));
    }

    @Override
    public ProxyConfigHolder getProxy() throws IOException {
        List<ProxyCredentials> proxyCredentials = getProxyCredentialsFromFile();
        List<ProxyNetworkConfig> proxyNetworkConfigs = getProxyNetworkConfigFromFile();
        List<ProxyConfigHolder> proxyConfigHolders = new ArrayList<>();
        int difference = proxyCredentials.size() - proxyNetworkConfigs.size();
        int j = 0;
        for (int i = 0; i < proxyNetworkConfigs.size() + difference; i++) {
            if (i >= proxyNetworkConfigs.size()) {
                proxyConfigHolders.add(new ProxyConfigHolder(proxyNetworkConfigs.get(j), proxyCredentials.get(i)));
                j++;
                if (j == proxyNetworkConfigs.size()) j = 0;
            } else {
                proxyConfigHolders.add(new ProxyConfigHolder(proxyNetworkConfigs.get(i), proxyCredentials.get(i)));
            }
        }
        for (ProxyConfigHolder p : proxyConfigHolders) {
            return p;
        }
        return null;
    }
}


