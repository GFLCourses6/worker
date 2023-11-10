package executor.service.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigPropertiesLoader {

    public Properties loadConfigProperties(String filename) {
        Properties properties = new Properties();
        try (InputStream input = ConfigPropertiesLoader.class.getClassLoader().getResourceAsStream(filename)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error reading settings from " + filename + " file");
        }
        return properties;
    }
}
