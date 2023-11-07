package executor.service.testComponent;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.annotation.Autowired;
import executor.service.service.listener.ScenarioSourceListener;

public class TestAutowiredConstructorComponent {

    public ObjectMapper objectMapper;
    public ScenarioSourceListener scenarioSourceListener;

    @Autowired
    public TestAutowiredConstructorComponent(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public TestAutowiredConstructorComponent(ObjectMapper objectMapper, ScenarioSourceListener scenarioSourceListener) {
        this.objectMapper = objectMapper;
        this.scenarioSourceListener = scenarioSourceListener;
    }
}
