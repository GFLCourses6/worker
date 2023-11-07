package executor.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.annotation.Bean;
import executor.service.annotation.Configuration;

@Configuration
public class ComponentConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
