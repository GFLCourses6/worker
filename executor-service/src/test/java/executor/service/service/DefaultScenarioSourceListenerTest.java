package executor.service.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.service.listener.DefaultScenarioSourceListener;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.lang.reflect.Field;
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
    void testExecute(Queue<Scenario> expectedScenarioQueue)
            throws IOException, NoSuchFieldException, IllegalAccessException {

        scenarioSourceListener.execute();
        var scenarioQueue = retrieveScenarioQueue(scenarioQueueHolder);

        assertArrayEquals(expectedScenarioQueue.toArray(), scenarioQueue.toArray());
    }

    @SuppressWarnings("unchecked")
    private Queue<Scenario> retrieveScenarioQueue(ScenarioQueueHolder holder)
            throws NoSuchFieldException, IllegalAccessException {

        Field scenariosField = ScenarioQueueHolder.class.getDeclaredField("scenarios");
        scenariosField.setAccessible(true);
        return (Queue<Scenario>) scenariosField.get(holder);
    }
}
