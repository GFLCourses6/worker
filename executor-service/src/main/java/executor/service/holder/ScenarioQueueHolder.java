package executor.service.holder;

import executor.service.model.Scenario;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScenarioQueueHolder implements QueueHolder<Scenario> {

    private final Queue<Scenario> scenarios = new LinkedBlockingQueue<>();

    @Override
    public Queue<Scenario> getQueue() {
        return scenarios;
    }
}
