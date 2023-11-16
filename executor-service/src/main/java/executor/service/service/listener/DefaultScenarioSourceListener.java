package executor.service.service.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exception.ScenarioSourceExecutionException;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Queue;

public class DefaultScenarioSourceListener implements ScenarioSourceListener {

    private static final String SCENARIOS_PATH = "src/json/Scenarios.json";
    private final ObjectMapper objectMapper;
    private final Queue<Scenario> scenarioQueue;

    public DefaultScenarioSourceListener(ObjectMapper objectMapper, ScenarioQueueHolder scenarioQueueHolder) {
        this.objectMapper = objectMapper;
        this.scenarioQueue = scenarioQueueHolder.getQueue();
    }

    @Override
    public void execute() {
        List<Scenario> scenarios = readScenarios();
        scenarioQueue.addAll(scenarios);
    }

    private List<Scenario> readScenarios() {
        try {
            return objectMapper.readValue(
                    new File(SCENARIOS_PATH),
                    objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, Scenario.class)
            );
        } catch (IOException e) {
            throw new ScenarioSourceExecutionException(
                    String.format("Wasn't able to parse scenarios from file: %s", SCENARIOS_PATH)
            );
        }
    }
}
