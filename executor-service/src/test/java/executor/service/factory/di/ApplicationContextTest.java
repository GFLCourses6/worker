package executor.service.factory.di;

import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exception.ComponentCreationException;
import executor.service.exception.ImplCountException;
import executor.service.holder.ScenarioQueueHolder;
import executor.service.model.Step;
import executor.service.model.ThreadPoolConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.listener.DefaultScenarioSourceListener;
import executor.service.service.listener.ScenarioSourceListener;
import executor.service.service.step.StepExecution;
import executor.service.service.step.impl.StepExecutionClickCss;
import executor.service.testComponent.TestAutowiredConstructorComponent;
import executor.service.testComponent.TestComponentWithDependencies;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationContextTest {

    private ComponentFactory componentFactory;

    @BeforeEach
    void setUp() {
        componentFactory = new ApplicationContext();
    }

    @Test
    @Order(1)
    @DisplayName("Test - create component using class, expected fail with ComponentCreationException " +
            "because it's impossible to inject dependencies in the constructor")
    void testCreateFromClassNegative() {
        assertThrows(ComponentCreationException.class, () ->
                componentFactory.getComponent(Step.class));
    }

    @Test
    @Order(1)
    @DisplayName("Test - create component with 0 params in constructor via class, expected ok")
    void testCreateFromClassPositive() {
        StepExecutionClickCss actualStep = componentFactory.getComponent(StepExecutionClickCss.class);

        assertNotNull(actualStep);
        assertEquals("clickCss", actualStep.getStepAction());
    }

    @Test
    @Order(1)
    @DisplayName("Test - pull component from one of the config classes via class")
    void testPullFromConfigClassViaClass() {
        ObjectMapper objectMapper = componentFactory.getComponent(ObjectMapper.class);

        assertNotNull(objectMapper);
    }

    @Test
    @Order(1)
    @DisplayName("Test - pull component from one of the config classes via abstract class / interface")
    void testPullFromConfigClassViaAbstractClassOrInterface() {
        ObjectCodec objectMapper = componentFactory.getComponent(ObjectCodec.class);

        assertNotNull(objectMapper);
        assertTrue(objectMapper instanceof ObjectMapper);
    }

    @Test
    @DisplayName("Test - pull component with from one of the config classes' methods with dependencies")
    void testPullFromConfigClassWithDependencies() {
        TestComponentWithDependencies component = componentFactory.getComponent(TestComponentWithDependencies.class);

        assertNotNull(component);
        assertNotNull(component.componentDependency);
        assertNotNull(component.objectMapper);
    }

    @Test
    @DisplayName("Test - create component with constructor dependencies via interface, expected ok")
    void testCreateFromInterfacePositive() throws NoSuchFieldException, IllegalAccessException {
        ScenarioSourceListener scenarioSourceListener = componentFactory.getComponent(ScenarioSourceListener.class);

        assertTrue(scenarioSourceListener instanceof DefaultScenarioSourceListener);
        assertNotNull(getPrivateField(scenarioSourceListener, "objectMapper", ObjectMapper.class));
        assertNotNull(getPrivateField(scenarioSourceListener, "scenarioQueueHolder", ScenarioQueueHolder.class));
    }

    @Test
    @DisplayName("Test - try to create a component, but the interface has multiple implementations, " +
            "expected fail with ImplCountException")
    void testCreateFromInterfaceNegative() {
        assertThrows(ImplCountException.class, () ->
                componentFactory.getComponent(StepExecution.class));
    }

    @Test
    @DisplayName("Test - try to create a component, with the @Autowired constructor, expected ok")
    void testCreateWithAutowiredConstructor() {
        TestAutowiredConstructorComponent component =
                componentFactory.getComponent(TestAutowiredConstructorComponent.class);

        assertNotNull(component);
        assertNotNull(component.objectMapper);
        assertNull(component.scenarioSourceListener);
    }

    @Test
    @DisplayName("Test - assert that during the second and the following creations of the component," +
            " it'll be pulled from the holder, expected ok")
    void testNoDuplicatesCreated() {
        ObjectMapper objectMapper1 = componentFactory.getComponent(ObjectMapper.class);
        ObjectMapper objectMapper2 = componentFactory.getComponent(ObjectMapper.class);
        ObjectCodec objectMapper3 = componentFactory.getComponent(ObjectCodec.class);

        assertNotNull(objectMapper1);
        assertSame(objectMapper1, objectMapper2);
        assertSame(objectMapper2, objectMapper3);
    }

    @Test
    @DisplayName("Test - assert that ThreadPoolConfig is created with the proper configs from 'config.properties' " +
            "inside the @Configuration class, expected ok")
    void testThreadPoolConfigBeanCreation() {
        ThreadPoolConfig threadPoolConfig = componentFactory.getComponent(ThreadPoolConfig.class);

        assertNotNull(threadPoolConfig);
        assertEquals(10, threadPoolConfig.getCorePoolSize());
        assertEquals(2L, threadPoolConfig.getKeepAliveTime());
    }

    @Test
    @DisplayName("Test - assert that WebDriverConfig is created with the proper configs from 'config.properties' " +
            "inside the @Configuration class, expected ok")
    void testWebDriverConfig() {
        WebDriverConfig webDriverConfig = componentFactory.getComponent(WebDriverConfig.class);

        assertNotNull(webDriverConfig);
        assertEquals("path/to/chromedriver.exe", webDriverConfig.getWebDriverExecutable());
        assertEquals("Mozilla/5.0", webDriverConfig.getUserAgent());
        assertEquals(50L, webDriverConfig.getPageLoadTimeout());
        assertEquals(20L, webDriverConfig.getImplicitlyWait());
    }

    private  <T> Object getPrivateField(Object obj, String fieldName, Class<T> fieldType)
            throws NoSuchFieldException, IllegalAccessException {

        Class<?> objClass = obj.getClass();
        Field field = objClass.getDeclaredField(fieldName);
        field.setAccessible(true);
        return fieldType.cast(field.get(obj));
    }
}
