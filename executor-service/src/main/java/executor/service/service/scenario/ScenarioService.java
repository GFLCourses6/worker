package executor.service.service.scenario;

import executor.service.model.dto.Scenario;

import java.util.List;

public interface ScenarioService {

    void saveScenario(Scenario scenario);

    List<Scenario> getScenariosByUsername(String username);

    Scenario getScenarioByName(String scenarioName);

    void saveScenarios(List<Scenario> scenarios);

    List<Scenario> getScenariosByUsernameAndScenarioName(String username, String scenarioName);
}
