package executor.service.testComponent;

import com.fasterxml.jackson.databind.ObjectMapper;

public class TestComponentWithDependencies {

    public ObjectMapper objectMapper;
    public TestAutowiredConstructorComponent componentDependency;

    public TestComponentWithDependencies(ObjectMapper objectMapper,
                                         TestAutowiredConstructorComponent componentDependency) {
        this.objectMapper = objectMapper;
        this.componentDependency = componentDependency;
    }
}