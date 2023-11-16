package executor.service.holder;


import executor.service.model.ProxyNetworkConfig;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyNetworkConfigHolder implements QueueHolder<ProxyNetworkConfig> {

    private final Queue<ProxyNetworkConfig> networkConfigs = new LinkedBlockingQueue<>();

    @Override
    public Queue<ProxyNetworkConfig> getQueue() {
        return networkConfigs;
    }
}
