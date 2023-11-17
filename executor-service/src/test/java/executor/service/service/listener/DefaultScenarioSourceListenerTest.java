package executor.service.service.listener;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import executor.service.util.file.FileParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class DefaultScenarioSourceListenerTest {

    @Mock
    private FileParser fileParser;
    private DefaultScenarioSourceListener scenarioSourceListener;
    private ScenarioQueueHolder scenarioQueueHolder;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        scenarioQueueHolder = new ScenarioQueueHolder();
        scenarioSourceListener = new DefaultScenarioSourceListener(
                scenarioQueueHolder, fileParser
        );
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.DefaultScenarioSourceListenerParams#testExecute")
    void testExecute(Queue<Scenario> expectedScenarioQueue) throws IOException {
        when(fileParser.getAllFromFile(any(String.class), eq(Scenario.class))).thenReturn(
                expectedScenarioQueue.stream().toList()
        );
        scenarioSourceListener.execute();
        var scenarioQueue = scenarioQueueHolder.getQueue();

        assertArrayEquals(expectedScenarioQueue.toArray(), scenarioQueue.toArray());
    }
}
