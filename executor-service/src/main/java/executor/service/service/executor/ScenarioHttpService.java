package executor.service.service.executor;

import executor.service.model.Scenario;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ScenarioHttpService
        implements ScenarioService {

    private final Queue<Scenario> scenarioQueue = new LinkedBlockingQueue<>();

    @Override
    public void saveScenario(final Scenario scenario) {
        scenarioQueue.add(scenario);
    }

    @Override
    public Queue<Scenario> getScenarioQueue() {
        return scenarioQueue;
    }

    public Scenario getScenarioByName(final String scenarioName) {
        return scenarioQueue
                .stream()
                .filter(scenario -> scenario.getName().equals(scenarioName))
                .findFirst()
                .orElse(null);
    }
}
