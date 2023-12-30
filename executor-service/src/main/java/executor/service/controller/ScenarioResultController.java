package executor.service.controller;

import executor.service.model.entity.ScenarioResult;
import executor.service.service.result.ScenarioResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/api")
public class ScenarioResultController {

    private final ScenarioResultService scenarioResultService;

    public ScenarioResultController(ScenarioResultService scenarioResultService) {
        this.scenarioResultService = scenarioResultService;
    }

    @GetMapping
    public ResponseEntity<List<ScenarioResult>> getAllScenarioResults() {
        List<ScenarioResult> scenarioResults =
                scenarioResultService.getAllScenarioResults();
        return scenarioResults.isEmpty()
               ? ResponseEntity.noContent().build()
               : ResponseEntity.ok(scenarioResults);
    }

    @PostMapping(value = "/result", consumes = "application/json")
    public ResponseEntity<ScenarioResult> createScenarioResult(
            @RequestBody ScenarioResult scenarioResult) {
        return new ResponseEntity<>(scenarioResultService.createScenarioResult(
                scenarioResult), HttpStatus.CREATED);
    }
}
