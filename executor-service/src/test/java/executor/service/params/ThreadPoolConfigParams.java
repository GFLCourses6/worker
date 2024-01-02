package executor.service.params;

import executor.service.model.dto.ThreadPoolConfig;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

public class ThreadPoolConfigParams {

    static Stream<Arguments> testAllArgsConstructor() {
        return Stream.of(
                // expectedCorePoolSize, expectedKeepAliveTime
                Arguments.of(10, 100L),
                Arguments.of(15, 150L)
        );
    }

    static Stream<Arguments> testGetters() {
        ThreadPoolConfig tpc1 = new ThreadPoolConfig(10, 100L);
        ThreadPoolConfig tpc2 = new ThreadPoolConfig(15, 150L);

        return Stream.of(
                // ThreadPoolConfig dto, Integer expectedCorePoolSize, Long expectedKeepAliveTime
                Arguments.of(tpc1, 10, 100L),
                Arguments.of(tpc2, 15, 150L)
        );
    }

    static Stream<Arguments> testSetters() {
        ThreadPoolConfig tpc1 = new ThreadPoolConfig();
        ThreadPoolConfig tpc2 = new ThreadPoolConfig();

        return Stream.of(
                // ThreadPoolConfig dto, Integer expectedCorePoolSize, Long expectedKeepAliveTime
                Arguments.of(tpc1, 10, 100L),
                Arguments.of(tpc2, 15, 150L)
        );
    }

    static Stream<Arguments> testEqualsAndHashCodePositive() {
        return Stream.of(
                // ThreadPoolConfig dto1, ThreadPoolConfig dto2
                Arguments.of(
                        new ThreadPoolConfig(10, 100L),
                        new ThreadPoolConfig(10, 100L)
                ),
                Arguments.of(
                        new ThreadPoolConfig(15, 150L),
                        new ThreadPoolConfig(15, 150L)
                )
        );
    }

    static Stream<Arguments> testEqualsNegative() {
        return Stream.of(
                // ThreadPoolConfig dto1, ThreadPoolConfig dto2
                Arguments.of(
                        new ThreadPoolConfig(10, 100L),
                        new ThreadPoolConfig(15, 150L)
                ),
                Arguments.of(
                        new ThreadPoolConfig(12, 120L),
                        new ThreadPoolConfig(12, 150L)
                )
        );
    }
}
