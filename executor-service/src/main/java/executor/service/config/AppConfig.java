package executor.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.facade.parallel.ConfigurableThreadPool;
import executor.service.model.ThreadPoolConfig;
import executor.service.model.WebDriverConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Import({WebDriverConfig.class, ThreadPoolConfig.class})
public class AppConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        return new ConfigurableThreadPool(threadPoolConfig);
    }
}
