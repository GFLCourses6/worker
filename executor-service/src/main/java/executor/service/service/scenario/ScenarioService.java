package executor.service.service.scenario;

import executor.service.model.dto.Scenario;

public interface ScenarioService {

    void saveScenario(Scenario scenario);
    Scenario getScenarioByName(String scenarioName);
}