package executor.service.holder;

import executor.service.model.ProxyCredentials;

import java.util.Collection;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyCredentialsHolder implements QueueHolder<ProxyCredentials> {
    private final Queue<ProxyCredentials> credentials = new LinkedBlockingQueue<>();

    @Override
    public void add(ProxyCredentials proxyCredential) {
        credentials.add(proxyCredential);
    }

    @Override
    public void addAll(Collection<ProxyCredentials> newProxyCredentials) {
        credentials.addAll(newProxyCredentials);
    }

    @Override
    public Optional<ProxyCredentials> poll() {
        return Optional.ofNullable(credentials.poll());
    }
}
