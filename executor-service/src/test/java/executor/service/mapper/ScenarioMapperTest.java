package executor.service.mapper;

import executor.service.model.dto.Scenario;
import executor.service.model.dto.Step;
import executor.service.model.entity.ScenarioResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class ScenarioMapperTest {

    @Autowired
    private ScenarioMapper scenarioMapper;

    @ParameterizedTest
    @MethodSource("scenarioMapperArgsSource")
    void testScenarioMapper(ScenarioResult source) {
        var actual = scenarioMapper.scenarioResultToScenarioResponse(source);

        assertEquals(source.getId(), actual.getId());
        assertEquals(source.getName(), actual.getName());
        assertEquals(source.getName(), actual.getName());
        assertEquals(source.getUsername(), actual.getUsername());
        assertEquals(source.getStepResults().size(), actual.getStepResults().size());
    }

    static Stream<Arguments> scenarioMapperArgsSource() {
        return Stream.of(
                Arguments.of(
                        new ScenarioResult(
                                new Scenario(
                                        "name", "site",
                                        null, List.of()
                                )
                        )
                ),
                Arguments.of(
                        new ScenarioResult(
                                new Scenario(
                                        "name", "site",
                                        null, List.of(
                                        new Step("action1", "value1"),
                                        new Step("action1", "value1")
                                ))
                        )
                )
        );
    }
}
