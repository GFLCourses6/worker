package executor.service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.annotation.Bean;
import executor.service.annotation.Configuration;
import executor.service.facade.parallel.ConfigurableThreadPool;
import executor.service.model.ThreadPoolConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.ConfigPropertiesLoader;

import java.util.Properties;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
public class ComponentConfiguration {
    
    private static final String CONFIG_PROPS_PATH = "config.properties";

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public WebDriverConfig webDriverConfig(ConfigPropertiesLoader configPropertiesLoader) {
        Properties properties = configPropertiesLoader.loadConfigProperties(CONFIG_PROPS_PATH);
        WebDriverConfig webDriverConfig = new WebDriverConfig();

        webDriverConfig.setWebDriverExecutable(properties.getProperty("webDriverExecutable"));
        webDriverConfig.setUserAgent(properties.getProperty("userAgent"));
        webDriverConfig.setPageLoadTimeout(Long.parseLong(properties.getProperty("pageLoadTimeout")));
        webDriverConfig.setImplicitlyWait(Long.parseLong(properties.getProperty("implicitlyWait")));

        return webDriverConfig;
    }

    @Bean
    public ThreadPoolConfig threadPoolConfig(ConfigPropertiesLoader configPropertiesLoader) {
        Properties properties = configPropertiesLoader.loadConfigProperties(CONFIG_PROPS_PATH);
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

        threadPoolConfig.setCorePoolSize(Integer.parseInt(properties.getProperty("corePoolSize")));
        threadPoolConfig.setKeepAliveTime(Long.parseLong(properties.getProperty("keepAliveTime")));

        return threadPoolConfig;
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(ThreadPoolConfig threadPoolConfig) {
        return new ConfigurableThreadPool(threadPoolConfig);
    }
}
