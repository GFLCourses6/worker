package executor.service.controller;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.service.scenario.result.ScenarioResultService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("/result")
public class ScenarioResultController {

    private final ScenarioResultService scenarioResultService;
    @Value("${client.auth.token.header.name}")
    private String authTokenHeaderName;
    @Value("${client.service.auth.token.value}")
    private String authTokenValue;
    public ScenarioResultController(
            final ScenarioResultService resultService) {
        this.scenarioResultService = resultService;
    }

    @GetMapping
    public ResponseEntity<List<ScenarioResultResponse>> getAllScenarioResults() {
        List<ScenarioResultResponse> scenarioResults =
                scenarioResultService.getAllScenarioResults();

        HttpHeaders headers = new HttpHeaders();
        headers.add(authTokenHeaderName, authTokenValue);

        return scenarioResults.isEmpty()
                ? ResponseEntity.noContent().headers(headers).build()
                : ResponseEntity.ok().headers(headers).body(scenarioResults);
    }
}
