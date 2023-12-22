package executor.service.service.listener;

import executor.service.exception.FileReadException;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.util.FileParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

@Service
public class DefaultScenarioSourceListener implements ScenarioSourceListener {

    private static final String SCENARIOS_PATH = "json/Scenarios.json";
    private final Queue<Scenario> scenarioQueue;
    private final FileParser fileParser;

    @Autowired
    public DefaultScenarioSourceListener(
            ScenarioQueueHolder scenarioQueueHolder,
            @Qualifier("fileJsonParser") FileParser fileParser) {
        this.scenarioQueue = scenarioQueueHolder.getQueue();
        this.fileParser = fileParser;
    }

    @Override
    public void execute() {
        List<Scenario> scenarios = readScenarios();
        scenarioQueue.addAll(scenarios);
    }

    private List<Scenario> readScenarios() {
        try {
            return fileParser.getAllFromFile(SCENARIOS_PATH, Scenario.class);
        } catch (IOException e) {
            throw new FileReadException(
                    String.format("Wasn't able to parse scenarios from file: %s", SCENARIOS_PATH)
            );
        }
    }
}
