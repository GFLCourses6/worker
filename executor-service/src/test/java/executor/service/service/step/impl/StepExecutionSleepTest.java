package executor.service.service.step.impl;

import executor.service.exception.StepExecutionInterruptedException;
import executor.service.exception.StepNumberFormatException;
import executor.service.model.Step;
import executor.service.service.step.StepExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Step step = new Step("sleep", "5:10");
        assertDoesNotThrow(() -> stepExecutionSleep.step(webDriver, step));
    }
    @Test
    void testStepThrowsNumberFormatException() {
        Step step = new Step("sleep", "invalid");
        assertThrows(StepNumberFormatException.class, () -> stepExecutionSleep.step(webDriver, step));
    }

    @Test
    void testStepThrowsExecutionInterruptedException() throws InterruptedException {
        Step step = new Step("sleep", "2:5");
        Thread.currentThread().interrupt();
        assertThrows(StepExecutionInterruptedException.class, () -> stepExecutionSleep.step(webDriver, step));
    }
}