package executor.service.service.proxy;

import executor.service.model.ProxyConfigHolder;

import java.io.IOException;

public interface ProxySourcesClient {
    ProxyConfigHolder getProxy() throws IOException;
}
