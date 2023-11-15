package executor.service.holder;

import executor.service.model.ProxyCredentials;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyCredentialsHolder extends AbstractQueueHolder<ProxyCredentials> {

    private final Queue<ProxyCredentials> credentials = new LinkedBlockingQueue<>();

    @Override
    Queue<ProxyCredentials> getQueue() {
        return credentials;
    }
}
