package executor.service.model;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ThreadPoolConfigTest {

    @Test
    @Order(0)
    void testDefaultConstructor() {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig();

        assertNull(threadPoolConfig.getCorePoolSize());
        assertNull(threadPoolConfig.getKeepAliveTime());
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.ThreadPoolConfigParams#testAllArgsConstructor")
    void testAllArgsConstructor(Integer expectedCorePoolSize, Long expectedKeepAliveTime) {
        ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(expectedCorePoolSize, expectedKeepAliveTime);

        assertEquals(expectedCorePoolSize, threadPoolConfig.getCorePoolSize());
        assertEquals(expectedKeepAliveTime, threadPoolConfig.getKeepAliveTime());
    }

    @ParameterizedTest
    @Order(1)
    @MethodSource("executor.service.params.ThreadPoolConfigParams#testGetters")
    void testGetters(ThreadPoolConfig dto, Integer expectedCorePoolSize, Long expectedKeepAliveTime) {
        Integer actualCorePoolSize = dto.getCorePoolSize();
        Long actualKeepAliveTime = dto.getKeepAliveTime();

        assertEquals(expectedCorePoolSize, actualCorePoolSize);
        assertEquals(expectedKeepAliveTime, actualKeepAliveTime);
    }

    @ParameterizedTest
    @Order(2)
    @MethodSource("executor.service.params.ThreadPoolConfigParams#testSetters")
    void testSetters(ThreadPoolConfig dto, Integer expectedCorePoolSize, Long expectedKeepAliveTime) {
        dto.setCorePoolSize(expectedCorePoolSize);
        dto.setKeepAliveTime(expectedKeepAliveTime);

        assertEquals(dto.getCorePoolSize(), expectedCorePoolSize);
        assertEquals(dto.getKeepAliveTime(), expectedKeepAliveTime);
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.ThreadPoolConfigParams#testEqualsAndHashCodePositive")
    void testEqualsAndHashCodePositive(ThreadPoolConfig dto1, ThreadPoolConfig dto2) {
        assertEquals(dto1, dto2);
        assertEquals(dto1.hashCode(), dto2.hashCode());
    }

    @ParameterizedTest
    @MethodSource("executor.service.params.ThreadPoolConfigParams#testEqualsNegative")
    void testEqualsNegative(ThreadPoolConfig dto1, ThreadPoolConfig dto2) {
        assertNotEquals(dto1, dto2);
    }
}
