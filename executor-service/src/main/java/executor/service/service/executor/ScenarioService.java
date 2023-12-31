package executor.service.service.executor;

import executor.service.model.Scenario;

import java.util.Queue;

public interface ScenarioService {

    void saveScenario(Scenario scenario);

    Queue<Scenario> getScenarioQueue();

    Scenario getScenarioByName(String scenarioName);
}
