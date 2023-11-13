package executor.service.holder;


import executor.service.model.ProxyNetworkConfig;

import java.util.Collection;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyNetworkConfigHolder implements QueueHolder<ProxyNetworkConfig> {
    private final Queue<ProxyNetworkConfig> networkConfigs = new LinkedBlockingQueue<>();



    @Override
    public void add(ProxyNetworkConfig networkConfig) {
        networkConfigs.add(networkConfig);
    }

    @Override
    public void addAll(Collection<ProxyNetworkConfig> newNetworkConfig) {
        networkConfigs.addAll(newNetworkConfig);
    }

    @Override
    public Optional<ProxyNetworkConfig> poll() {
        return Optional.ofNullable(networkConfigs.poll());
    }
}
