package executor.service.params;

import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Scenario;
import org.junit.jupiter.params.provider.Arguments;

import java.util.List;
import java.util.stream.Stream;

public class ScenariosQueueHolderParams {

    static Stream<Arguments> testPoll() {
        ScenarioQueueHolder holder1 = new ScenarioQueueHolder();
        Scenario expectedScenario1 = new Scenario();
        holder1.getQueue().addAll(List.of(expectedScenario1, new Scenario(), new Scenario()));

        ScenarioQueueHolder holder2 = new ScenarioQueueHolder();
        Scenario expectedScenario2 = new Scenario();
        holder2.getQueue().addAll(List.of(expectedScenario2, new Scenario()));

        // ScenarioQueueHolder scenariosHolder, Scenario expectedScenario
        return Stream.of(
                Arguments.of(holder1, expectedScenario1),
                Arguments.of(holder2, expectedScenario2),
                Arguments.of(new ScenarioQueueHolder(), null)
        );
    }

    static Stream<Arguments> testAdd() {
        // ScenarioQueueHolder holder, Scenario scenario
        return Stream.of(
                Arguments.of(new ScenarioQueueHolder(), new Scenario())
        );
    }

    static Stream<Arguments> testAddAll() {
        // ScenarioQueueHolder holder, List<Scenario> newScenarios
        return Stream.of(
                Arguments.of(new ScenarioQueueHolder(), List.of(new Scenario(), new Scenario()))
        );
    }
}
