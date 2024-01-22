package executor.service.controller;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.service.scenario.result.ScenarioResultService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{username}")
    public ResponseEntity<List<ScenarioResultResponse>> getAllScenarioResults(@PathVariable String username) {
        List<ScenarioResultResponse> scenarioResults = scenarioResultService.getAllScenarioResults(username);
        return scenarioResults.isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(scenarioResults);
    }
}