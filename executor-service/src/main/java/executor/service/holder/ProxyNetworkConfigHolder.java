package executor.service.holder;


import executor.service.model.ProxyNetworkConfig;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyNetworkConfigHolder extends AbstractQueueHolder<ProxyNetworkConfig> {

    private final Queue<ProxyNetworkConfig> networkConfigs = new LinkedBlockingQueue<>();

    @Override
    Queue<ProxyNetworkConfig> getQueue() {
        return networkConfigs;
    }
}
