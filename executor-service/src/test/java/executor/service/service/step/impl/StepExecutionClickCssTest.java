package executor.service.service.step.impl;

import executor.service.model.entity.ExecutionStatus;
import executor.service.model.dto.Step;
import executor.service.model.entity.StepResult;
import executor.service.service.step.StepExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


class StepExecutionClickCssTest {

    private StepExecution stepExecutionClickCss;
    private WebDriver webDriver;
    private Step mockStep;

    @BeforeEach
    public void setUp() {
        mockStep = mock(Step.class);
        webDriver = mock(WebDriver.class);
        stepExecutionClickCss = new StepExecutionClickCss();
    }

    @AfterEach
    void tearDown() {
        stepExecutionClickCss = null;
        webDriver = null;
    }

    @Test
    void testGetStepAction() {
        String action = stepExecutionClickCss.getStepAction();
        assertEquals("clickCss", action);
    }

    @Test
    void testStepExecutionClickCssWhenFound() {
        Step step = new Step("clickCss", ".test-css-selector");
        WebElement webElement = mock(WebElement.class);
        when(webDriver.findElement(By.cssSelector(step.getValue()))).thenReturn(webElement);
        stepExecutionClickCss.step(webDriver, step);
        verify(webElement).click();
    }

    @Test
    @DisplayName("When step method is called with an invalid cssSelector, then ExecutionStatus.FAIL is returned")
    void testStepReturnsExecutionStatusFail() {
        String wrongSelector = "cssSelector";
        when(mockStep.getValue()).thenReturn(wrongSelector);
        when(webDriver.findElement(By.cssSelector(wrongSelector))).thenThrow(new WebDriverException());
        StepExecution stepExecutor = new StepExecutionClickCss();
        StepResult result = stepExecutor.step(webDriver, mockStep);
        assertEquals(ExecutionStatus.FAIL, result.getExecutionStatus());
    }

    @Test
    @DisplayName("step() returns StepResult with execution status ExecutionStatus.SUCCESS")
    void testStepReturnsExecutionStatusSuccess() {
        String rightSelector = "rightSelector";
        var mockWebElement = mock(WebElement.class);
        when(mockStep.getValue()).thenReturn(rightSelector);
        when(webDriver.findElement(any(By.class))).thenReturn(mockWebElement);
        StepExecution stepExecutor = new StepExecutionClickCss();
        StepResult result = stepExecutor.step(webDriver, mockStep);
        assertEquals(ExecutionStatus.SUCCESS, result.getExecutionStatus());
    }
}
