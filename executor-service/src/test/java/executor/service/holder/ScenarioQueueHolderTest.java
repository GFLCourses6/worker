package executor.service.holder;

import executor.service.model.Scenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScenarioQueueHolderTest {

    @ParameterizedTest
    @MethodSource("executor.service.params.ScenariosQueueHolderParams#testPoll")
    void testPoll(ScenarioQueueHolder holder, Scenario expectedScenario) {
        Assertions.assertEquals(holder.poll().orElse(null), expectedScenario);
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.ScenariosQueueHolderParams#testAdd")
    void testAdd(ScenarioQueueHolder holder, Scenario scenario)
            throws NoSuchFieldException, IllegalAccessException {

        var scenarios = retrieveScenarioQueue(holder);
        int initialLength = scenarios.size();

        holder.add(scenario);

        assertTrue(scenarios.contains(scenario));
        assertEquals(scenarios.size(), initialLength + 1);
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("executor.service.params.ScenariosQueueHolderParams#testAddAll")
    void testAddAll(ScenarioQueueHolder holder, List<Scenario> newScenarios)
            throws NoSuchFieldException, IllegalAccessException {

        var scenarios = retrieveScenarioQueue(holder);
        int initialLength = scenarios.size();

        holder.addAll(newScenarios);

        assertTrue(scenarios.containsAll(newScenarios));
        assertEquals(scenarios.size(), initialLength + newScenarios.size());
    }

    @SuppressWarnings("unchecked")
    private Queue<Scenario> retrieveScenarioQueue(ScenarioQueueHolder holder)
            throws NoSuchFieldException, IllegalAccessException {

        Field scenariosField = ScenarioQueueHolder.class.getDeclaredField("scenarios");
        scenariosField.setAccessible(true);
        return (Queue<Scenario>) scenariosField.get(holder);
    }
}
