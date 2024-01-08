package executor.service.service.handler;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.Scenario;
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;

@Service
public class ScenarioSourceQueueHandler
        implements SourceQueueHandler<Scenario> {

    private final BlockingQueue<Scenario> scenarios;

    public ScenarioSourceQueueHandler(ScenarioQueueHolder holder) {
        this.scenarios = holder.getQueue();
    }

    @Override
    public void enqueue(Scenario scenario) {
        scenarios.add(scenario);
    }

    @Override
    public Scenario dequeue() {
        return scenarios.poll();
    }

    @Override
    public boolean isQueueEmpty() {
        return scenarios.isEmpty();
    }

    @Override
    public int getQueueSize() {
        return scenarios.size();
    }
}
