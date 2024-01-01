package executor.service.service.scenario.result;

import executor.service.model.entity.ScenarioResult;

import java.util.List;

public interface ScenarioResultService {
    ScenarioResult createScenarioResult(ScenarioResult result);

    List<ScenarioResult> getAllScenarioResults();
}
