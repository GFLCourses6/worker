package executor.service.util.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.dto.ProxyNetworkConfig;
import executor.service.model.dto.Scenario;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FileJsonParserTest {

    FileJsonParser fileJsonParser = new FileJsonParser(new ObjectMapper(), new DefaultResourceLoader());

    @ParameterizedTest
    @MethodSource("executor.service.params.DefaultScenarioSourceListenerParams#testExecute")
    void testGetAllFromFile(Queue<Scenario> expectedScenarios) throws IOException {
        List<Scenario> actual = fileJsonParser.getAllFromFile(
                "json/Scenario.json", Scenario.class);

        assertTrue(actual.containsAll(expectedScenarios));
    }

    @Test
    void testGetFromFile() throws IOException {
        ProxyNetworkConfig expected = new ProxyNetworkConfig("proxy1.example.com", 8080);
        ProxyNetworkConfig actual = fileJsonParser.getFromFile(
                "json/SingleProxyNetworkConfig.json", ProxyNetworkConfig.class);

        assertEquals(expected, actual);
    }
}
