package executor.service.service.proxy;

import executor.service.exception.FileReadException;
import executor.service.model.ProxyConfigHolder;
import executor.service.util.file.FileParser;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProxySourcesClientService implements ProxySourcesClient {

    private static final String PROXY_CONFIGS_PATH = "src/main/resources/json/ProxyConfigs.json";
    private final FileParser fileParser;
    private final BlockingQueue<ProxyConfigHolder> proxyConfigHolders;

    @Autowired
    public ProxySourcesClientService(FileParser fileParser) {
        this.fileParser = fileParser;
        this.proxyConfigHolders = new LinkedBlockingQueue<>(getConfigs());
    }

    private void fillProxyConfigHoldersQueue() {
        proxyConfigHolders.addAll(getConfigs());
    }

    private List<ProxyConfigHolder> getConfigs() {
        try {
            return fileParser.getAllFromFile(PROXY_CONFIGS_PATH, ProxyConfigHolder.class);
        } catch (IOException e) {
            throw new FileReadException(e.getMessage());
        }
    }

    @Override
    public ProxyConfigHolder getProxy() {
        ProxyConfigHolder proxyConfigHolder = proxyConfigHolders.peek();

        synchronized (this) {
            if (proxyConfigHolder == null) {
                fillProxyConfigHoldersQueue();
            }
        }
        return proxyConfigHolders.poll();
    }
}


