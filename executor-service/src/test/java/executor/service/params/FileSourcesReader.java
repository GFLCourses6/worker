package executor.service.params;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.exception.FileReadException;

import java.io.IOException;
import java.io.InputStream;

public final class FileSourcesReader {

    private FileSourcesReader() {
    }

    private static class Holder {
        private static final ObjectMapper objectMapper = new ObjectMapper();
    }

    public static <T> T readFile(
            final String filename,
            final Class<T> valueType) {
        try (InputStream inputStream = ClassLoader
                .getSystemResourceAsStream(filename)) {
            if (inputStream != null) {
                return Holder.objectMapper.readValue(inputStream, valueType);
            }
        } catch (IOException e) {
            throw new FileReadException("Failed to read file:", e);
        }
        throw new FileReadException(
                String.format("File not found: %s", filename));
    }
}
