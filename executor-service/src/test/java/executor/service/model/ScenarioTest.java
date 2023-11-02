package executor.service.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ScenarioTest {

  private Scenario scenario;

  @BeforeEach
  void setUp() {

    List<Step> steps = new ArrayList<>();
    steps.add(new Step("action1", "value1"));
    steps.add(new Step("action2", "value2"));
    scenario = new Scenario("TestScenario", "example.com", steps);
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
  void testScenarioEquals() {
    Scenario scenario2 = new Scenario("TestScenario", "example.com", scenario.getSteps());
    assertTrue(scenario.equals(scenario2));
  }

  @Test
  void testScenarioHashCode() {
    int hashCode1 = scenario.hashCode();
    Scenario scenario2 = new Scenario("TestScenario", "example.com", scenario.getSteps());
    int hashCode2 = scenario2.hashCode();
    assertEquals(hashCode1, hashCode2);
  }
}
