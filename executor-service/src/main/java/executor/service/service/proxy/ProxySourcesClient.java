package executor.service.service.proxy;

import executor.service.model.dto.ProxyConfigHolder;

public interface ProxySourcesClient {
    ProxyConfigHolder getProxy(String username);
}
