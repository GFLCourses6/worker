package executor.service.params;

import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.ProxyCredentials;
import executor.service.model.dto.ProxyNetworkConfig;
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
