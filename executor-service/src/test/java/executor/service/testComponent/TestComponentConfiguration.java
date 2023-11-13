package executor.service.testComponent;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.annotation.Bean;
import executor.service.annotation.Configuration;

@Configuration
public class TestComponentConfiguration {

    @Bean // args will be injected automatically
    public TestComponentWithDependencies test(ObjectMapper objectMapper,
                                              TestAutowiredConstructorComponent component) {
        return new TestComponentWithDependencies(objectMapper, component);
    }
}
