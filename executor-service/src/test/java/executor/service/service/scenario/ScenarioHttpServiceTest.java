package executor.service.service.scenario;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
class ScenarioHttpServiceTest {

    @Mock
    private ScenarioQueueHolder scenarioQueueHolder;
    private ScenarioHttpService scenarioHttpService;
    private BlockingQueue<Scenario> scenarioQueue = spy(new LinkedBlockingQueue<>());

    @BeforeEach
    void setUp() {
        when(scenarioQueueHolder.getQueue()).thenReturn(scenarioQueue);
        scenarioHttpService = new ScenarioHttpService(scenarioQueueHolder);
    }

    @Test
    void testSaveScenario() {
        var scenario = mock(Scenario.class);

        scenarioHttpService.saveScenario(scenario);

        verify(scenarioQueue).add(scenario);
    }

    @Test
    void testGetScenarioByName() {
        var expected = new Scenario("expected", "site", new ArrayList<>());
        scenarioQueue.addAll(List.of(new Scenario(), expected, new Scenario()));

        var actual = scenarioHttpService.getScenarioByName("expected");
        assertEquals(expected, actual);
    }
}
