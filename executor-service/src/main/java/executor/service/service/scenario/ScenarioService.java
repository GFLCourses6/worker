package executor.service.service.scenario;

import executor.service.model.dto.Scenario;

import java.util.List;

public interface ScenarioService {

    void saveScenario(Scenario scenario);
    Scenario getScenarioByName(String scenarioName);
    void saveScenarios(List<Scenario> scenarios);
}
