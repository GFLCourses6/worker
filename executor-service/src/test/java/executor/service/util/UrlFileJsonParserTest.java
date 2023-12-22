package executor.service.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.config.AppConfig;
import executor.service.model.Scenario;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.test.context.TestPropertySource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestPropertySource(properties = {"file.source=classpath"})
class UrlFileJsonParserTest {

    private final ApplicationContextRunner contextRunner =
            new ApplicationContextRunner();

    private final FileJsonParser fileJsonParser =
            new FileJsonParser(new ObjectMapper());
    private final FileParser urlFileJsonParser =
            new UrlFileJsonParser(new ObjectMapper());

    @Test
    void testUrlFileParsing()
            throws IOException {
        String urlPath = "https://raw.githubusercontent.com/GFLCourses6/worker/main/executor-service/src/main/resources/json/Scenarios.json";
        List<Scenario> scenarios = urlFileJsonParser.getAllFromFile(urlPath, Scenario.class);
        assertNotNull(scenarios);
    }

    @Test
    void testGetFromFileWithURISyntaxExceptions() {
        String urlPath = "https";
        Class<Scenario> type = Scenario.class;
        assertThrows(FileNotFoundException.class,
                     () -> urlFileJsonParser.getAllFromFile(urlPath, type));
    }

    @Test
    void testGetFromFileWithURISyntaxException() {
        String urlPath = "https";
        assertThrows(FileNotFoundException.class,
                     () -> urlFileJsonParser.getFromFile(urlPath, Scenario.class));
    }

    @Test
    void whenValueSetToURLThenUseUrlFileJsonParserReturnNotNull() {
        assertNotNull(urlFileJsonParser);
    }


    void whenValueNotSetThenUseFileJsonParserWhenCreateFileJsonParser() {
        this.contextRunner
                .withPropertyValues("file.source=classpath")
                .withUserConfiguration(AppConfig.class)
                .run(context -> {
                    assertNotNull(context.getBean(FileJsonParser.class));
                    assertNull(context.getBean(UrlFileJsonParser.class, true));
                });
    }


    void whenValueSetToURLThenUseUrlFileJsonParserWhenCreateUrlFileJsonParser() {
        this.contextRunner
                .withPropertyValues("file.source=url")
                .withUserConfiguration(AppConfig.class)
                .run(context -> {
                    assertNotNull(context.getBean(UrlFileJsonParser.class));
                    assertNull(context.getBean(FileJsonParser.class, true));
                });
    }
}

