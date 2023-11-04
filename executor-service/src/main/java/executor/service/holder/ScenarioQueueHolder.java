package executor.service.holder;

import executor.service.model.Scenario;

import java.util.Collection;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ScenarioQueueHolder implements QueueHolder<Scenario> {

    private final Queue<Scenario> scenarios = new LinkedBlockingQueue<>();

    @Override
    public void add(Scenario scenario) {
        scenarios.add(scenario);
    }

    @Override
    public void addAll(Collection<Scenario> newScenarios) {
        scenarios.addAll(newScenarios);
    }

    @Override
    public Optional<Scenario> poll() {
        return Optional.ofNullable(scenarios.poll());
    }
}
