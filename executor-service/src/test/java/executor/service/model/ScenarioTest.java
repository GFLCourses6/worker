package executor.service.model;

import executor.service.model.dto.Scenario;
import executor.service.model.dto.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScenarioTest {

  private Scenario scenario;

  @BeforeEach
  void setUp() {
    List<Step> steps = new ArrayList<>();
    steps.add(new Step("action1", "value1"));
    steps.add(new Step("action2", "value2"));
    scenario = new Scenario("TestScenario", "example.com", null, steps);
  }

  @Test
  void testEmptyConstructor() {
    Scenario emptyScenario = new Scenario();

    assertNull(emptyScenario.getName());
    assertNull(emptyScenario.getSite());
    assertNull(emptyScenario.getSteps());
  }

  @Test
  void testScenarioName() {
    assertEquals("TestScenario", scenario.getName());
  }

  @Test
  void testScenarioSite() {
    assertEquals("example.com", scenario.getSite());
  }

  @Test
  void testScenarioSteps() {
    List<Step> steps = scenario.getSteps();
    assertNotNull(steps);
    assertEquals(2, steps.size());
    assertEquals("action1", steps.get(0).getAction());
    assertEquals("value1", steps.get(0).getValue());
    assertEquals("action2", steps.get(1).getAction());
    assertEquals("value2", steps.get(1).getValue());
  }
  @Test
  void testGettersAndSetters() {
    Scenario newScenario = new Scenario();
    assertNull(newScenario.getName());
    assertNull(newScenario.getSite());
    assertNull(newScenario.getSteps());

    newScenario.setName("NewTestScenario");
    newScenario.setSite("new-example.com");

    List<Step> newSteps = new ArrayList<>();
    newSteps.add(new Step("newAction1", "newValue1"));
    newSteps.add(new Step("newAction2", "newValue2"));
    newScenario.setSteps(newSteps);

    assertEquals("NewTestScenario", newScenario.getName());
    assertEquals("new-example.com", newScenario.getSite());
    assertEquals(2, newScenario.getSteps().size());
    assertEquals("newAction1", newScenario.getSteps().get(0).getAction());
    assertEquals("newValue1", newScenario.getSteps().get(0).getValue());
    assertEquals("newAction2", newScenario.getSteps().get(1).getAction());
    assertEquals("newValue2", newScenario.getSteps().get(1).getValue());
  }
  @Test
  void testScenarioHashCodeEquals() {
    Scenario scenario1 = new Scenario("Scenario1", "example.com",null, Collections.emptyList());
    Scenario scenario2 = new Scenario("Scenario1", "example.com",null, Collections.emptyList());

    assertEquals(scenario1.hashCode(), scenario2.hashCode());
  }
  @Test
  void testNullValues() {
    Scenario scenario = new Scenario();

    assertNull(scenario.getName());
    assertNull(scenario.getSite());
    assertNull(scenario.getSteps());

    scenario.setName(null);
    scenario.setSite(null);
    scenario.setSteps(null);

    assertNull(scenario.getName());
    assertNull(scenario.getSite());
    assertNull(scenario.getSteps());
  }
  @Test
  void testScenarioNotEqualsToOtherTypes() {
    Scenario scenario = new Scenario("Scenario1", "example.com", null, Collections.emptyList());
    Step step = new Step("action1", "value1");

    assertFalse(scenario.equals(step));
  }
  @Test
  void testScenarioWithEmptySteps() {
    Scenario scenario = new Scenario("EmptyScenario", "example.com", null, Collections.emptyList());

    assertTrue(scenario.getSteps().isEmpty());
  }
  @Test
  void testScenarioSetSteps() {
    List<Step> steps = new ArrayList<>();
    steps.add(new Step("action1", "value1"));
    steps.add(new Step("action2", "value2"));

    Scenario scenario = new Scenario("TestScenario", "example.com", null, steps);

    List<Step> newSteps = new ArrayList<>();
    newSteps.add(new Step("newAction1", "newValue1"));
    newSteps.add(new Step("newAction2", "newValue2"));

    scenario.setSteps(newSteps);

    assertEquals(newSteps, scenario.getSteps());
  }
  @Test
  void testNullAndEmptyValues() {
    Scenario scenario = new Scenario("TestScenario", "example.com", null, Collections.emptyList());

    scenario.setName(null);
    scenario.setSite("");
    Step step = new Step(null, "value");

    assertNull(scenario.getName());
    assertTrue(scenario.getSite().isEmpty());
    assertNull(step.getAction());
  }
}
