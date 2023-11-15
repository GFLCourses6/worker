package executor.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.annotation.Bean;
import executor.service.annotation.Configuration;
import executor.service.model.ThreadPoolConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.ConfigPropertiesLoader;

import java.util.Properties;

@Configuration
public class ComponentConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebDriverConfig webDriverConfig(ConfigPropertiesLoader configPropertiesLoader) {
        Properties properties = configPropertiesLoader.loadConfigProperties("config.properties");
        WebDriverConfig webDriverConfig = new WebDriverConfig();

        webDriverConfig.setUserAgent(properties.getProperty("userAgent"));
        webDriverConfig.setPageLoadTimeout(Long.parseLong(properties.getProperty("pageLoadTimeout")));
        webDriverConfig.setImplicitlyWait(Long.parseLong(properties.getProperty("implicitlyWait")));

        return webDriverConfig;
    }

    @Bean
    public ThreadPoolConfig threadPoolConfig(ConfigPropertiesLoader configPropertiesLoader) {
        Properties properties = configPropertiesLoader.loadConfigProperties("config.properties");
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

        threadPoolConfig.setCorePoolSize(Integer.parseInt(properties.getProperty("corePoolSize")));
        threadPoolConfig.setKeepAliveTime(Long.parseLong(properties.getProperty("keepAliveTime")));

        return threadPoolConfig;
    }
}
