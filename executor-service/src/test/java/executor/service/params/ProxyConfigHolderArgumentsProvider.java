package executor.service.params;

import executor.service.model.dto.ProxyConfigHolder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Stream;

public class ProxyConfigHolderArgumentsProvider  implements ArgumentsProvider {
    private static final String filename = "json/ProxyConfigData.json";

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext context) {
        // Read JSON file content and convert it into ProxyConfigHolder objects
        List<ProxyConfigHolder> proxyConfigHolders = Arrays.asList(
                FileSourcesReader.readFile(filename, ProxyConfigHolder[].class)
        );

        // Create a Queue of ProxyConfigHolder instances
        Queue<ProxyConfigHolder> proxyConfigHolderQueue = new LinkedList<>(proxyConfigHolders);

        // Return a single argument containing the queue
        return Stream.of(Arguments.of(proxyConfigHolderQueue));
    }
}
