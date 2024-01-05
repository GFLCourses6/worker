package executor.service.config;

import executor.service.facade.parallel.ConfigurableThreadPool;
import executor.service.model.dto.ThreadPoolConfig;
import executor.service.model.dto.WebDriverConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Import({WebDriverConfig.class, ThreadPoolConfig.class})
public class AppConfig {

    private static final String EXTENSION_PATH =
            "classpath:MultiPass-for-HTTP-basic-authentication.crx";

    @Bean
    public ThreadPoolExecutor threadPoolExecutor(
            final ThreadPoolConfig threadPoolConfig) {
        return new ConfigurableThreadPool(threadPoolConfig);
    }

    @Bean
    public File extensionFile() throws IOException {
        Resource resource = new DefaultResourceLoader().getResource(EXTENSION_PATH);
        InputStream inputStream = resource.getInputStream();
        Path tempFile = Files.createTempFile("extension", ".crx");
        Files.copy(inputStream, tempFile, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
        return tempFile.toFile();
    }
}
