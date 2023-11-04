package executor.service.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DefaultScenarioSourceListener implements ScenarioSourceListener {

    private static final String SCENARIOS_PATH = "src/main/resources/Scenarios.json";
    private final ObjectMapper objectMapper;
    private final ScenarioQueueHolder scenarioQueueHolder;

    public DefaultScenarioSourceListener(ObjectMapper objectMapper, ScenarioQueueHolder scenarioQueueHolder) {
        this.objectMapper = objectMapper;
        this.scenarioQueueHolder = scenarioQueueHolder;
    }

    @Override
    public void execute() throws IOException {
        List<Scenario> scenarios = objectMapper.readValue(
                new File(SCENARIOS_PATH),
                objectMapper.getTypeFactory().constructCollectionType(List.class, Scenario.class)
        );
        scenarioQueueHolder.addAll(scenarios);
    }
}
