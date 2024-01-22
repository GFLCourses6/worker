package executor.service.service.scenario.result;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.model.entity.ScenarioResult;

import java.util.List;

public interface ScenarioResultService {
    ScenarioResultResponse createScenarioResult(ScenarioResult result);

    List<ScenarioResultResponse> getAllScenarioResults(String username);
}
