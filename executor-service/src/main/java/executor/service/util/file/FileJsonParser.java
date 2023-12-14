package executor.service.util.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FileJsonParser implements FileParser {

    private final ObjectMapper objectMapper;

    @Autowired
    public FileJsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> List<T> getAllFromFile(String filePath, Class<T> type) throws IOException {
        return objectMapper.readValue(
                new File(filePath),
                objectMapper.getTypeFactory()
                        .constructCollectionType(List.class, type));
    }

    @Override
    public <T> T getFromFile(String filePath, Class<T> type) throws IOException {
        return objectMapper.readValue(new File(filePath), type);
    }
}
