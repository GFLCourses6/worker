package executor.service.holder;

import executor.service.model.Scenario;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScenarioQueueHolder extends AbstractQueueHolder<Scenario> {

    private final Queue<Scenario> scenarios = new LinkedBlockingQueue<>();

    @Override
    Queue<Scenario> getQueue() {
        return scenarios;
    }
}
