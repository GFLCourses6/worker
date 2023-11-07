package executor.service.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProxyConfigHolderTest {

    private ProxyConfigHolder proxyConfigHolder;
    private ProxyNetworkConfig proxyNetworkConfig;
    private ProxyCredentials proxyCredentials;

    @BeforeEach
    void setUp() {
        proxyNetworkConfig = new ProxyNetworkConfig("hostname", 8888);
        proxyCredentials = new ProxyCredentials("username", "password");
        proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
    }

    @Test
    void testDefaultConstructor() {
        ProxyConfigHolder actualProxyConfigHolder = new ProxyConfigHolder();

        assertNull(actualProxyConfigHolder.getProxyNetworkConfig(), "proxyNetworkConfig should be null");
        assertNull(actualProxyConfigHolder.getProxyCredentials(), "proxyCredentials should be null");
    }

    @Test
    void testParametrizedConstructor() {
        assertEquals(proxyNetworkConfig, proxyConfigHolder.getProxyNetworkConfig(), "proxyNetworkConfig objects should be equal");
        assertEquals(proxyCredentials, proxyConfigHolder.getProxyCredentials(), "proxyCredentials objects should be equal");
    }

    @Test
    void testGetProxyNetworkConfig() {
        assertEquals(proxyNetworkConfig, proxyConfigHolder.getProxyNetworkConfig(), "proxyNetworkConfig objects should be equal");
    }

    @Test
    void testGetProxyCredentials() {
        assertEquals(proxyCredentials, proxyConfigHolder.getProxyCredentials(), "proxyCredentials objects should be equal");
    }

    @Test
    void testSetProxyNetworkConfig() {
        ProxyNetworkConfig expectedProxyNetworkConfig = new ProxyNetworkConfig("hostname", 8888);
        proxyConfigHolder.setProxyNetworkConfig(expectedProxyNetworkConfig);

        assertEquals(expectedProxyNetworkConfig, proxyConfigHolder.getProxyNetworkConfig(), "proxyNetworkConfig objects should be equal");
    }

    @Test
    void testSetProxyCredentials() {
        ProxyCredentials expectedProxyCredentials = new ProxyCredentials("username", "password");
        proxyConfigHolder.setProxyCredentials(expectedProxyCredentials);

        assertEquals(expectedProxyCredentials, proxyConfigHolder.getProxyCredentials(), "proxyCredentials objects should be equal");
    }

    @Test
    void testEqualsAndHashCodePositive() {
        ProxyConfigHolder proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);

        assertEquals(proxyConfigHolder.hashCode(), proxyConfigHolder2.hashCode(), "proxyConfigHolder objects hashcode should be equal");
        assertEquals(proxyConfigHolder, proxyConfigHolder2, "proxyConfigHolder objects should be equal");
    }

    @Test
    void testEqualsNegative() {
        ProxyNetworkConfig proxyNetworkConfig2 = new ProxyNetworkConfig("anotherHostname", 9999);
        ProxyConfigHolder proxyConfigHolder2 = new ProxyConfigHolder(proxyNetworkConfig2, proxyCredentials);

        assertNotEquals(proxyConfigHolder, proxyConfigHolder2, "proxyConfigHolder objects should not be equal");
    }

    @Test
    void testToString() {
        String expectedString = "ProxyConfigHolder{proxyNetworkConfig=ProxyNetworkConfig{hostname='hostname', port=8888}, proxyCredentials=ProxyCredentials{username='username', password='password'}}";
        System.out.println(proxyConfigHolder);

        assertEquals(expectedString, proxyConfigHolder.toString(), "String objects should be equal");
    }
}
