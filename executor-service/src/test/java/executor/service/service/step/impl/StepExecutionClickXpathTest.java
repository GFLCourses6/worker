package executor.service.service.step.impl;

import executor.service.model.ExecutionStatus;
import executor.service.model.Step;
import executor.service.model.StepResult;
import executor.service.service.step.StepExecution;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class StepExecutionClickXpathTest {
    private StepExecutionClickXpath stepExecutionClickXpath;
    private WebDriver webDriver;

    @BeforeEach
    void setUp() {
        stepExecutionClickXpath = new StepExecutionClickXpath();
    }

    @AfterEach
    void tearDown() {
        stepExecutionClickXpath = null;
        webDriver = null;
    }

    @Test
    void testGetStepAction() {
        String action = stepExecutionClickXpath.getStepAction();
        assertEquals("clickXpath", action);
    }

    @Test
    void testStepExecutionClickXpathWhenFound() {
        webDriver = mock(WebDriver.class);
        Step step = new Step("clickXpath", ".test-xpath");
        WebElement mockElement = mock(WebElement.class);
        when(webDriver.findElement(By.xpath(step.getValue()))).thenReturn(mockElement);
        stepExecutionClickXpath.step(webDriver, step);
        verify(mockElement).click();
    }

    @Test
    void testStepReturnsExecutionStatusFail() {
        String wrongSelector = "cssSelector";
        var mockStep = mock(Step.class);
        webDriver = mock(WebDriver.class);
        when(mockStep.getValue()).thenReturn(wrongSelector);
        when(webDriver.findElement(By.xpath(wrongSelector))).thenThrow(new WebDriverException());
        StepExecution stepExecutor = new StepExecutionClickXpath();
        StepResult result = stepExecutor.step(webDriver, mockStep);
        assertEquals(ExecutionStatus.FAIL, result.getExecutionStatus());
    }
}