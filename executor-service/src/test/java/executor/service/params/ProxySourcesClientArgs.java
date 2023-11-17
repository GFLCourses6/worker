package executor.service.params;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class ProxySourcesClientArgs {

    static Stream<Arguments> getProxy() {
        ProxyConfigHolder noCredentialsHolder = new ProxyConfigHolder();
        noCredentialsHolder.setProxyNetworkConfig(new ProxyNetworkConfig("hostname", 8080));

        return Stream.of(
                Arguments.of(
                        new ProxyConfigHolder(
                                new ProxyNetworkConfig("hostname", 8080),
                                new ProxyCredentials("name", "password")
                        )
                ),
                Arguments.of(
                        noCredentialsHolder
                )
        );
    }
}
