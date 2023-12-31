package executor.service.controller;

import executor.service.model.dto.Scenario;
import executor.service.service.scenario.ScenarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @PostMapping("/queue")
    public ResponseEntity<Void> createScenario(
            @RequestBody final List<Scenario> scenarios) {
        scenarioService.saveScenarios(scenarios);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
