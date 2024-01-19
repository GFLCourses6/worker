package executor.service.controller;

import executor.service.model.dto.Scenario;
import executor.service.service.scenario.ScenarioService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
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
    @Value("${client.auth.token.header.name}")
    private String authTokenHeaderName;
    @Value("${client.auth.token.value}")
    private String authTokenValue;
    public ScenarioController(final ScenarioService scenarioService) {
        this.scenarioService = scenarioService;
    }

    @GetMapping("/queue/{username}")
    public ResponseEntity<List<Scenario>> getScenariosByUsername(
            @PathVariable final String username) {
        List<Scenario> scenarios = scenarioService.getScenariosByUsername(username);

        HttpHeaders headers = new HttpHeaders();
        headers.add(authTokenHeaderName, authTokenValue);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(scenarios);
    }

    @GetMapping("/queue/{username}/{scenarioName}")
    public ResponseEntity<List<Scenario>> getScenariosByName(
            @PathVariable final String username,
            @PathVariable final String scenarioName) {
        List<Scenario> scenarios = scenarioService.getScenariosByUsernameAndScenarioName(username, scenarioName);

        HttpHeaders headers = new HttpHeaders();
        headers.add(authTokenHeaderName, authTokenValue);

        return ResponseEntity
                .ok()
                .headers(headers)
                .body(scenarios);
    }

    @PostMapping("/queue")
    public ResponseEntity<Void> createScenario(
            @RequestBody final List<Scenario> scenarios) {
        scenarioService.saveScenarios(scenarios);

        HttpHeaders headers = new HttpHeaders();
        headers.add(authTokenHeaderName, authTokenValue);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .headers(headers)
                .build();
    }
}
