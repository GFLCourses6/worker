package executor.service.config;

import executor.service.facade.parallel.ConfigurableThreadPool;
import executor.service.model.dto.ThreadPoolConfig;
import executor.service.model.dto.WebDriverConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Import({WebDriverConfig.class, ThreadPoolConfig.class})
public class AppConfig {

    @Value("${proxy.extension.path}")
    private String extensionPath;

    private final ResourceLoader resourceLoader;

    public AppConfig(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(
            final ThreadPoolConfig threadPoolConfig) {
        return new ConfigurableThreadPool(threadPoolConfig);
    }

    @Bean
    @Qualifier("extensionFile")
    public File extensionFile() throws IOException {
        Resource resource = resourceLoader.getResource(extensionPath);
        InputStream inputStream = resource.getInputStream();
        Path tempFile = Files.createTempFile("extension", ".crx");
        Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toFile();
    }
}
