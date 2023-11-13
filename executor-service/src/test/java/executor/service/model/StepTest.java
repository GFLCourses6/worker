package executor.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StepTest {

    private Step step;

    @BeforeEach
    void setUp() {
        step = new Step("action", "value");
    }

    @Test
    @DisplayName("Test empty constructor with null action and value")
    void testEmptyConstructor(){
        Step emptyStep = new Step();

        assertNull(emptyStep.getAction());
        assertNull(emptyStep.getValue());
    }

    @Test
    @DisplayName("Test getting action from Step object")
    void testStepGetAction(){
        assertEquals("action", step.getAction());
    }
    @Test
    @DisplayName("Test getting value from Step object")
    void testStepGetValue(){
        assertEquals("value", step.getValue());
    }
    @Test
    @DisplayName("Test setting action in Step object")
    void testStepSetAction(){
        assertEquals("action", step.getAction());
        step.setAction("newAction");
        assertEquals("newAction", step.getAction());
    }

    @Test
    @DisplayName("Test setting value in Step object")
    void testStepSetValue(){
        assertEquals("value", step.getValue());
        step.setValue("newValue");
        assertEquals("newValue", step.getValue());
    }

    @Test
    @DisplayName("Test equality between Step objects")
    void testEquals() {
        Step sameStep = new Step("action", "value");
        assertEquals(step, sameStep);
    }

    @Test
    @DisplayName("Test inequality between Step objects")
    void testEqualsFailed() {
        Step anotherStep = new Step("action1", "value1");
        assertNotEquals(step, anotherStep);
    }
    @Test
    @DisplayName("Test hashCode method for Step object")
    void testHashCode() {
        int expectedHashCode = step.hashCode();
        assertEquals(expectedHashCode, step.hashCode());
    }

    @Test
    @DisplayName("Test toString method for Step object")
    void testToString() {
        String expectedString = "Step{action='action', value='value'}";
        assertEquals(expectedString, step.toString());
    }

    @Test
    @DisplayName("Test equality and hashCode for Step objects with the same values")
    void testEqualsAndHashCode() {
        Step step1 = new Step();
        step1.setValue("value");
        step1.setAction("action");

        Step step2 = new Step();
        step2.setValue("value");
        step2.setAction("action");

        assertEquals(step1, step2);
        assertEquals(step1.hashCode(), step2.hashCode());
    }
}
