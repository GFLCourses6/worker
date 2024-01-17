package executor.service.controller;

import executor.service.model.dto.Scenario;
import executor.service.service.scenario.ScenarioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/source")
public class ScenarioSourceController {


    private final ScenarioService scenarioService;

    public ScenarioSourceController(ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @PostMapping
    public void saveScenarios(@RequestBody List<@Valid Scenario> scenarios) {
        scenarioService.saveScenarios(scenarios);
    }
    @PutMapping
    public void saveScenario(@Valid Scenario scenario){
        scenarioService.saveScenario(scenario);
    }

    @GetMapping()
    public List<Scenario> getScenariosByUsernameAndScenarioName(@RequestParam(required = false) String username,
                                                                @RequestParam(required = false) String scenarioName){
        if(scenarioName == null){
            return scenarioService.getScenariosByUsername(username);
        }
        return scenarioService.getScenariosByUsernameAndScenarioName(username, scenarioName);
    }

    @GetMapping("/scenarioname/{scenarioName}")
    public Scenario getScenarioByName(@PathVariable String scenarioName) {
        return scenarioService.getScenarioByName(scenarioName);
    }

}
