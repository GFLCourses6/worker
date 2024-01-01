package executor.service.service.scenario;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import org.springframework.stereotype.Service;

import java.util.Queue;

@Service
public class ScenarioHttpService
        implements ScenarioService {

    private final Queue<Scenario> scenarioQueue;

    public ScenarioHttpService(ScenarioQueueHolder scenarioQueueHolder) {
        this.scenarioQueue = scenarioQueueHolder.getQueue();
    }

    @Override
    public void saveScenario(final Scenario scenario) {
        scenarioQueue.add(scenario);
    }

    @Override
    public Scenario getScenarioByName(final String scenarioName) {
        return scenarioQueue
                .stream()
                .filter(scenario -> scenarioName.equals(scenario.getName()))
                .findFirst()
                .orElse(null);
    }
}
