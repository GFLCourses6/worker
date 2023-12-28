package executor.service.service.step.impl;

import executor.service.model.entity.ExecutionStatus;
import executor.service.model.Step;
import executor.service.model.entity.StepResult;
import executor.service.service.step.StepExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;


class StepExecutionSleepTest {
    private StepExecution stepExecutionSleep;
    @Mock
    private WebDriver webDriver;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        stepExecutionSleep = new StepExecutionSleep();
    }

    @AfterEach
    void tearDown() {
        stepExecutionSleep = null;
        webDriver = null;
    }

    @Test
    void testGetStepAction() {
        String action = stepExecutionSleep.getStepAction();
        assertEquals("sleep", action);
    }

    @Test
    void testStepExecutionSleep() {
        Step step = new Step("sleep", "5");
        StepResult result = stepExecutionSleep.step(webDriver, step);
        assertEquals(ExecutionStatus.SUCCESS, result.getExecutionStatus());
    }

    @Test
    void testStepReturnsExecutionStatusFail() {
        Step step = new Step("sleep", "invalid");
        var result = stepExecutionSleep.step(webDriver, step);
        assertEquals(ExecutionStatus.FAIL, result.getExecutionStatus());
    }
}