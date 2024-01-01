package executor.service.service.executor.result;

import executor.service.dao.ScenarioResultRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class JpaScenarioResultServiceTest {

    AutoCloseable autoCloseable;

    @Mock
    private ScenarioResultRepository repository;

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
        ScenarioResult savedResult = scenarioResultService.createScenarioResult(scenarioResult);
        verify(repository, times(1)).save(scenarioResult);
        assertEquals(scenarioResult, savedResult);
    }

    @Test
    void testGetAllScenarioResults() {
        List<ScenarioResult> expectedResultList = Arrays.asList(
                new ScenarioResult(), new ScenarioResult());
        when(repository.findAllFetchStepResults()).thenReturn(expectedResultList);
        List<ScenarioResult> actualResultList = scenarioResultService.getAllScenarioResults();
        verify(repository, times(1)).findAllFetchStepResults();
        assertEquals(expectedResultList, actualResultList);
    }
}
