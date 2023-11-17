package executor.service.service.proxy;

import executor.service.exception.FileReadException;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.util.file.FileParser;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxySourcesClientService implements ProxySourcesClient {

    private static final String PROXY_CREDENTIALS_PATH = "src/json/ProxyCredentials.json";
    private static final String PROXY_NETWORK_CONFIG_PATH = "src/json/ProxyNetworkConfig.json";
    private final FileParser fileParser;
    private final Queue<ProxyNetworkConfig> networkConfigQueue;
    private final Queue<ProxyCredentials> credentialsQueue;

    public ProxySourcesClientService(FileParser fileParser) {
        this.fileParser = fileParser;
        this.credentialsQueue = getCredentials();
        this.networkConfigQueue = getConfigs();
    }

    private Queue<ProxyNetworkConfig> getConfigs() {
        try {
            return new LinkedBlockingQueue<>(
                    fileParser.getAllFromFile(PROXY_NETWORK_CONFIG_PATH, ProxyNetworkConfig.class)
            );
        } catch (IOException e) {
            throw new FileReadException("Wasn't able to read " + PROXY_NETWORK_CONFIG_PATH);
        }
    }

    private Queue<ProxyCredentials> getCredentials() {
        try {
            return new LinkedBlockingQueue<>(
                    fileParser.getAllFromFile(PROXY_CREDENTIALS_PATH, ProxyCredentials.class)
            );
        } catch (IOException e) {
            throw new FileReadException("Wasn't able to read " + PROXY_CREDENTIALS_PATH);
        }
    }

    @Override
    public ProxyConfigHolder getProxy() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
        proxyConfigHolder.setProxyNetworkConfig(networkConfigQueue.poll());
        proxyConfigHolder.setProxyCredentials(credentialsQueue.poll());

        if (proxyConfigHolder.getProxyNetworkConfig() == null) {
            throw new NoSuchElementException("ProxyNetworkConfig is null");
        }
        return proxyConfigHolder;
    }
}


