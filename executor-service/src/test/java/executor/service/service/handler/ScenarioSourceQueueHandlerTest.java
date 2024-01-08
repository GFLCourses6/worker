package executor.service.service.handler;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScenarioSourceQueueHandlerTest {
    private ScenarioSourceQueueHandler handler;

    @BeforeEach
    void setup(){
        ScenarioQueueHolder holder = new ScenarioQueueHolder();
        handler = new ScenarioSourceQueueHandler(holder);
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.DefaultScenarioSourceListenerParams#testExecute")
    void testEnqueueAndDequeue(Queue<Scenario> scenarios) {
        scenarios.forEach(handler::enqueue);
        assertFalse(handler.isQueueEmpty());
        assertEquals(scenarios.size(), handler.getQueueSize());
        scenarios.forEach(expected -> assertEquals(expected, handler.dequeue()));
        assertTrue(handler.isQueueEmpty());
        assertEquals(0, handler.getQueueSize());
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.DefaultScenarioSourceListenerParams#testExecute")
    void testIsQueueEmpty(Queue<Scenario> scenarios) {
        assertTrue(handler.isQueueEmpty());
        scenarios.forEach(handler::enqueue);
        assertFalse(handler.isQueueEmpty());
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.DefaultScenarioSourceListenerParams#testExecute")
    void testGetQueueSize(Queue<Scenario> scenarios) {
        assertEquals(0, handler.getQueueSize());
        scenarios.forEach(handler::enqueue);
        assertEquals(scenarios.size(), handler.getQueueSize());
    }
}
