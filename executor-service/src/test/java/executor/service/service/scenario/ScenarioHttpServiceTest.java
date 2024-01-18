package executor.service.service.scenario;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.dto.Scenario;
import executor.service.params.ScenariosArgumentsProvider;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class ScenarioHttpServiceTest {

    @Mock
    private ScenarioQueueHolder scenarioQueueHolder;
    private ScenarioHttpService scenarioHttpService;
    private final BlockingQueue<Scenario> scenarioQueue = spy(new LinkedBlockingQueue<>());

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
        var expected = new Scenario("expected", "site", null, new ArrayList<>());
        scenarioQueue.addAll(List.of(new Scenario(), expected, new Scenario()));

        var actual = scenarioHttpService.getScenarioByName("expected");
        assertEquals(expected, actual);
    }

    @ParameterizedTest
    @ArgumentsSource(ScenariosArgumentsProvider.class)
    void testCreateScenarios(List<Scenario> scenarios)  {
        assertNotNull(scenarios);
        when(scenarioQueueHolder.getQueue()).thenReturn(scenarioQueue);
        scenarioHttpService.saveScenarios(scenarios);
        assertEquals(2, scenarios.size());
        verify(scenarioQueueHolder, times(1)).getQueue();
        verify(scenarioQueue, times(1)).addAll(scenarios);
    }

    @ParameterizedTest
    @ArgumentsSource(ScenariosArgumentsProvider.class)
    void testGetScenarioByUsername(List<Scenario> scenarios) {
        scenarioHttpService.saveScenarios(scenarios);
        scenarios.stream()
                .map(scenario -> scenarioHttpService.getScenariosByUsernameAndScenarioName(
                        scenario.getUsername(),
                        scenario.getName()))
                .forEach(Assertions::assertNotNull);
    }

    @ParameterizedTest
    @ArgumentsSource(ScenariosArgumentsProvider.class)
    void testGetScenariosByUsername(List<Scenario> scenarios) {
        String username = "Alice";
        scenarioHttpService.saveScenarios(scenarios);
        List<Scenario> scenariosByUsername = scenarioHttpService
                .getScenariosByUsername(username);
        assertNotNull(scenariosByUsername);
        assertEquals(2, scenariosByUsername.size());
    }
}
