package executor.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component("fileJsonParser")
@ConditionalOnProperty(name = "file.source", havingValue = "classpath")
public class FileJsonParser implements FileParser {

    private final ObjectMapper objectMapper;

    public FileJsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> List<T> getAllFromFile(
            String filePath, Class<T> type)
            throws IOException {
        return objectMapper.readValue(
                new ClassPathResource(filePath).getInputStream(),
                objectMapper.getTypeFactory().constructCollectionType(
                         List.class, type));
    }

    @Override
    public <T> T getFromFile(
            String filePath, Class<T> type)
            throws IOException {
        return objectMapper.readValue(
                new ClassPathResource(filePath).getFile(),
                type);

    }
}
