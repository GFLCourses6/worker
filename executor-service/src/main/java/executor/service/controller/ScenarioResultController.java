package executor.service.controller;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.service.scenario.result.ScenarioResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/result")
public class ScenarioResultController {

    private final ScenarioResultService scenarioResultService;

    public ScenarioResultController(
            final ScenarioResultService resultService) {
        this.scenarioResultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<ScenarioResultResponse>> getAllScenarioResults() {
        List<ScenarioResultResponse> scenarioResults =
                scenarioResultService.getAllScenarioResults();
        return scenarioResults.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(scenarioResults);
    }
}