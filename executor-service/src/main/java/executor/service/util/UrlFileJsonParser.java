package executor.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exception.FileReadException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Component("urlFileJsonParser")
@ConditionalOnProperty(name = "file.source", havingValue = "url")
public class UrlFileJsonParser implements FileParser {

    private final ObjectMapper objectMapper;

    public UrlFileJsonParser(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> List<T> getAllFromFile(String path, Class<T> type) throws IOException {
        try {
            URI uri = new URI(path);
            if (!uri.isAbsolute()) {
                uri = new File(path).toURI();
            }
            return objectMapper.readValue(
                    uri.toURL(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, type));
        } catch (URISyntaxException e) {
            throw new FileReadException(e.getMessage());
        }
    }

    @Override
    public <T> T getFromFile(String path, Class<T> type) throws IOException {
        try {
            URI uri = new URI(path);
            if (!uri.isAbsolute()) {
                uri = new File(path).toURI();
            }
            return objectMapper.readValue(uri.toURL(), type);
        } catch (URISyntaxException e) {
            throw new FileReadException(e.getMessage());
        }
    }
}