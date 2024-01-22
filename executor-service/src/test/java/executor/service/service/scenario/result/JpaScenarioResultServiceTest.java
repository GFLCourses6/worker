package executor.service.service.scenario.result;

import executor.service.dao.ScenarioResultRepository;
import executor.service.mapper.ScenarioMapper;
import executor.service.model.entity.ScenarioResult;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
public class JpaScenarioResultServiceTest {

    @MockBean
    private ScenarioResultRepository scenarioResultRepository;

    @MockBean
    private ScenarioMapper scenarioMapper;

    @Autowired
    private JpaScenarioResultService resultService;

    @ParameterizedTest
    @MethodSource("createScenarioResultArgsSource")
    void testCreateScenarioResult(ScenarioResult result) {
        ScenarioResult persisted = mock();
        when(scenarioResultRepository.save(result)).thenReturn(persisted);

        resultService.createScenarioResult(result);

        verify(scenarioResultRepository).save(result);
        verify(scenarioMapper).scenarioResultToScenarioResponse(persisted);
    }

    static Stream<Arguments> createScenarioResultArgsSource() {
        return Stream.of(
                Arguments.of(new ScenarioResult())
        );
    }
}
