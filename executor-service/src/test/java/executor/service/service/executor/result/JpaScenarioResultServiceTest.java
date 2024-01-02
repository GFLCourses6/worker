package executor.service.service.executor.result;

import executor.service.dao.ScenarioResultRepository;
import executor.service.mapper.ScenarioMapper;
import executor.service.model.entity.ScenarioResult;
import executor.service.service.scenario.result.JpaScenarioResultService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class JpaScenarioResultServiceTest {

    AutoCloseable autoCloseable;

    @Mock
    private ScenarioResultRepository repository;

    @Mock
    private ScenarioMapper mapper;

    @InjectMocks
    private JpaScenarioResultService scenarioResultService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void testCreateScenarioResult() {
        ScenarioResult scenarioResult = new ScenarioResult();
        when(repository.save(scenarioResult)).thenReturn(scenarioResult);
        scenarioResultService.createScenarioResult(scenarioResult);
        verify(repository, times(1)).save(scenarioResult);
        verify(mapper, times(1)).scenarioResultToScenarioResponse(scenarioResult);
    }

    @Test
    void testGetAllScenarioResults() {
        List<ScenarioResult> expectedResultList = Arrays.asList(
                new ScenarioResult(), new ScenarioResult());
        when(repository.findAllFetchStepResults()).thenReturn(expectedResultList);
        scenarioResultService.getAllScenarioResults();
        verify(repository, times(1)).findAllFetchStepResults();
        verify(mapper, times(1)).scenarioResultToScenarioResponse(expectedResultList);
    }
}
