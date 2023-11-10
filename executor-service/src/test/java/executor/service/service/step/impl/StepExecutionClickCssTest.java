package executor.service.service.step.impl;

import executor.service.exception.StepExecutionException;
import executor.service.exception.StepExecutionInterruptedException;
import executor.service.exception.StepNumberFormatException;
import executor.service.model.Step;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


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
    @DisplayName("Given a ScenarioExecutor instance, when step method is called with an invalid selector, then a StepExecutionException is thrown")
    void testStepThrowsExceptionCssSelector() {
        String cssSelector = "cssSelector";
        when(mockStep.getValue()).thenReturn(cssSelector);
        when(webDriver.findElement(By.cssSelector(cssSelector)))
                .thenThrow(WebDriverException.class);
        StepExecution stepExecutor = new StepExecutionClickCss();
        assertThrows(StepExecutionException.class,
                () -> stepExecutor.step(webDriver, mockStep));
    }

    @Test
    @DisplayName("Given a ScenarioExecutor instance, when step method is called with an invalid xpath, then a StepExecutionException is thrown")
    void testStepThrowsException() {
        String cssSelector = "cssSelector";
        when(mockStep.getValue()).thenReturn(cssSelector);
        when(webDriver.findElement(By.xpath(cssSelector)))
                .thenThrow(WebDriverException.class);
        StepExecution stepExecutor = new StepExecutionClickXpath();
        assertThrows(StepExecutionException.class,
                () -> stepExecutor.step(webDriver, mockStep));
    }

    @Test
    @DisplayName("Given a ScenarioExecutor instance, when step method is called with an invalid duration value, then a StepNumberFormatException is thrown")
    void testStepThrowsNumberFormatException() {
        String value = "invalid";
        when(mockStep.getValue()).thenReturn(value);
        StepExecution stepExecutor = new StepExecutionSleep();
        assertThrows(StepNumberFormatException.class,
                () -> stepExecutor.step(webDriver, mockStep));
    }

   
}
