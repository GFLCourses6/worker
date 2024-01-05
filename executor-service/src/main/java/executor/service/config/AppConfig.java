package executor.service.config;

import executor.service.facade.parallel.ConfigurableThreadPool;
import executor.service.model.dto.ThreadPoolConfig;
import executor.service.model.dto.WebDriverConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Import({WebDriverConfig.class, ThreadPoolConfig.class})
public class AppConfig {

    @Value("${proxy.extension.path}")
    private String extensionPath;

    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(
            final ThreadPoolConfig threadPoolConfig) {
        return new ConfigurableThreadPool(threadPoolConfig);
    }

    @Bean
    public File extensionFile() throws IOException {
        Resource resource = resourceLoader.getResource(extensionPath);
        InputStream inputStream = resource.getInputStream();
        Path tempFile = Files.createTempFile("extension", ".crx");
        Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toFile();
    }
}
