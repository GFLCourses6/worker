package executor.service.config;

import executor.service.model.ThreadPoolConfig;
import executor.service.model.WebDriverConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({WebDriverConfig.class, ThreadPoolConfig.class})
public class AppConfig {
}
