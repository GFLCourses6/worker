package executor.service.config;

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
    public ThreadPoolExecutor threadPoolExecutor(
            final ThreadPoolConfig threadPoolConfig) {
        return new ConfigurableThreadPool(threadPoolConfig);
    }
}
