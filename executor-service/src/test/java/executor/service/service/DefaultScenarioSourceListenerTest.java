package executor.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.service.listener.DefaultScenarioSourceListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class DefaultScenarioSourceListenerTest {

    private DefaultScenarioSourceListener scenarioSourceListener;
    private ScenarioQueueHolder scenarioQueueHolder;

    @BeforeEach
    void setUp() {
        scenarioQueueHolder = new ScenarioQueueHolder();
        scenarioSourceListener = new DefaultScenarioSourceListener(
                new ObjectMapper(), scenarioQueueHolder
        );
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.DefaultScenarioSourceListenerParams#testExecute")
    void testExecute(Queue<Scenario> expectedScenarioQueue) {
        scenarioSourceListener.execute();
        var scenarioQueue = scenarioQueueHolder.getQueue();

        assertArrayEquals(expectedScenarioQueue.toArray(), scenarioQueue.toArray());
    }
}
