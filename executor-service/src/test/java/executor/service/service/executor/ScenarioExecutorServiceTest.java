package executor.service.service.executor;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.params.ActionsArgumentsProvider;
import executor.service.params.ScenariosArgumentsProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.mockito.Mock;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ScenarioExecutorServiceTest {

    @Mock
    private WebDriver webDriver;
    @Mock
    private Step click;
    @Mock
    private Step sleep;
    @Mock
    private Step clickCss;
    @Mock
    private Scenario scenario;
    @Mock
    private WebElement webElement;
    @Mock
    private ScenarioExecutor service;

    @BeforeEach
    public void setup() {
        sleep = new Step();
        click = new Step();
        clickCss = mock(Step.class);
        scenario = mock(Scenario.class);
        webDriver = mock(WebDriver.class);
        webElement = mock(WebElement.class);
        List<Step> steps = Arrays.asList(click, sleep, clickCss);
        when(scenario.getSteps()).thenReturn(steps);
        service = new ScenarioExecutorService();
    }

    @ParameterizedTest(name = "Step: action = {0}, value = {1}")
    @DisplayName("Given a ScenarioExecutor instance, when execute method is called, then the correct steps are executed")
    @CsvFileSource(resources = {"/csv/actions.csv"}, numLinesToSkip = 1)
    void testScenarioExecutor(
            String action,
            String value) {
        String site = "https://github.com";
        when(webDriver.findElement(any(By.class))).thenReturn(webElement);
        sleep.setValue("1:2");
        sleep.setAction("SLEEP");
        click.setAction(action);
        click.setValue(value);
        List<Step> steps = Arrays.asList(click, sleep);
        Scenario scenario = new Scenario();
        scenario.setSite(site);
        scenario.setSteps(steps);
        service.execute(scenario, webDriver);
        verify(webDriver).get(site);
        verify(webDriver).quit();
    }

    @ParameterizedTest
    @ArgumentsSource(ScenariosArgumentsProvider.class)
    @DisplayName("Given a List Scenarios instances, when execute method is called, then the correct steps are executed")
    void testScenarios(List<Scenario> scenarios) {
        when(webDriver.findElement(any(By.class))).thenReturn(webElement);
        scenarios.forEach(scenario -> service.execute(scenario, webDriver));
        scenarios.forEach(scenario -> {
            verify(webDriver, times(scenarios.size())).get(scenario.getSite());
            verify(webDriver, times(scenarios.size())).quit();
        });
    }

    @ParameterizedTest
    @ArgumentsSource(ActionsArgumentsProvider.class)
    @DisplayName("Given a Scenario instance, when List of steps method is called, then the correct element is clicked")
    void testStepsOfScenario(List<Step> steps) {
        String site = "https://github.com";
        when(webDriver.findElement(any(By.class))).thenReturn(webElement);
        Scenario scenario = new Scenario();
        scenario.setSite(site);
        scenario.setSteps(steps);
        service.execute(scenario, webDriver);
        verify(webElement, times(steps.size())).click();
        verify(webDriver).get(site);
        verify(webDriver).quit();
    }
}
