package executor.service.service.scenario;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.Scenario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
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
    public void saveScenarios(List<Scenario> scenarios) {
        scenarioQueue.addAll(scenarios);
    }

    @Override
    public List<Scenario>  getScenarioByUsername(
            final String username, final String scenarioName) {
        return getScenariosByUsername(username)
                    .stream()
                    .filter(s -> s.getName().equals(scenarioName))
                    .toList();
    }

    @Override
    public List<Scenario> getScenariosByUsername(
            final String username) {
        return scenarioQueue
                .stream()
                .filter(scenario -> scenario.getUsername().equals(username))
                .toList();
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
