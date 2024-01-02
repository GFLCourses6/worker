package executor.service.aop;

import executor.service.model.dto.ScenarioResultResponse;
import executor.service.model.entity.ScenarioResult;
import executor.service.service.scenario.result.ScenarioResultService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoggingAspectTest {
    @Mock
    private ScenarioResultService resultService;

    private LoggingAspect loggingAspect;

    AutoCloseable autoCloseable;

    @BeforeEach
    public void setup() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        loggingAspect = new LoggingAspect(resultService);
    }

    @AfterEach
    public void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void saveScenarioResultAfterExecutionAspect() {
        ScenarioResult scenarioResult = new ScenarioResult();
        ScenarioResultResponse scenarioResultResponse = new ScenarioResultResponse();
        when(resultService.createScenarioResult(scenarioResult)).thenReturn(scenarioResultResponse);
        loggingAspect.saveScenarioResultAfterExecutionAspect(scenarioResult);
        verify(resultService, times(1)).createScenarioResult(scenarioResult);
    }
}