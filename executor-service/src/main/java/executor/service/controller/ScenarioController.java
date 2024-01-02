package executor.service.controller;

import executor.service.model.dto.Scenario;
import executor.service.service.scenario.ScenarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/scenario")
public class ScenarioController {

    private final ScenarioService scenarioService;

    public ScenarioController(final ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/queue/{scenarioName}")
    public ResponseEntity<Scenario> getScenarioByName(
            @PathVariable final String scenarioName) {
        Scenario scenario = scenarioService.getScenarioByName(scenarioName);
        return scenario != null
                ? new ResponseEntity<>(scenario, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<Void> createScenario(
            @RequestBody final Scenario scenario) {
        scenarioService.saveScenario(scenario);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
