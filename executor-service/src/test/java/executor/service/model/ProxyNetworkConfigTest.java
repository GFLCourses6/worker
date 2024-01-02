package executor.service.model;

import executor.service.model.dto.ProxyNetworkConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
class ProxyNetworkConfigTest {
    private ProxyNetworkConfig config1;
    private ProxyNetworkConfig config2;

    @BeforeEach
    void setUp() {
        config1 = new ProxyNetworkConfig("hostname1", 8080);
        config2 = new ProxyNetworkConfig("hostname2", 9090);
    }

    @Test
    @DisplayName("Given ProxyNetworkConfig instance, when getHostname is called, then it should return the hostname")
    void getHostname() {
        assertEquals("hostname1", config1.getHostname());
        assertEquals("hostname2", config2.getHostname());
    }

    @Test
    @DisplayName("Given ProxyNetworkConfig instance, when setHostname is called, then it should update the hostname")
    void setHostname() {
        config1.setHostname("newHostname");
        assertEquals("newHostname", config1.getHostname());
    }

    @Test
    @DisplayName("Given ProxyNetworkConfig instance, when getPort is called, then it should return the port as expected")
    void getPort() {
        assertEquals(8080, config1.getPort());
        assertEquals(9090, config2.getPort());
    }

    @Test
    @DisplayName("Given ProxyNetworkConfig instance, when setPort is called, then it should update the port and return the new value as expected")
    void setPort() {
        config2.setPort(8888);
        assertEquals(8888, config2.getPort());
    }

    @Test
    @DisplayName("Given two ProxyNetworkConfig instances with the same hostname and port, when equals is called, then they should be equal")
    void testEquals() {
        ProxyNetworkConfig sameConfig =
                new ProxyNetworkConfig("hostname1", 8080);
        assertEquals(config1, sameConfig);
    }

    @Test
    @DisplayName("Given two ProxyNetworkConfig instances with different hostname and port, when equals is called, then they should not be equal")
    void testEquals_DifferentConfigurations() {
        assertNotEquals(config1, config2);
    }

    @Test
    @DisplayName("Given ProxyNetworkConfig instance, when hashCode is called, then it should return the expected hash code")
    void testHashCode() {
        int expectedHashCode = config1.hashCode();
        assertEquals(expectedHashCode, config1.hashCode());
    }

    @Test
    @DisplayName("Given ProxyNetworkConfig instance, when toString is called, then it should return the expected string representation")
    void testToString() {
        String expectedString = "ProxyNetworkConfig{hostname='hostname1', port=8080}";
        assertEquals(expectedString, config1.toString());
    }

    @Test
    @DisplayName("Given two ProxyNetworkConfig objects when they have the same hostname and port then they should be equal and have the same hashcode")
    void testEqualsAndHashCode() {
        ProxyNetworkConfig config1 = new ProxyNetworkConfig();
        config1.setHostname("localhost");
        config1.setPort(8080);

        ProxyNetworkConfig config2 = new ProxyNetworkConfig();
        config2.setHostname("localhost");
        config2.setPort(8080);

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
    }
}
