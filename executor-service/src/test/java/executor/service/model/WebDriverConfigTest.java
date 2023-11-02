package executor.service.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


public class WebDriverConfigTest {
    @Test
    public void testDefaultConstructor() {
        WebDriverConfig config = new WebDriverConfig();

        assertNull(config.getWebDriverExecutable());
        assertNull(config.getUserAgent());
        assertNull(config.getPageLoadTimeout());
        assertNull(config.getImplicitlyWait());
    }

    @Test
    public void testParameterizedConstructor() {
        String webDriverExecutable = "driver.exe";
        String userAgent = "TestUserAgent";
        Long pageLoadTimeout = 30L;
        Long implicitlyWait = 10L;

        WebDriverConfig config = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);

        assertEquals(webDriverExecutable, config.getWebDriverExecutable());
        assertEquals(userAgent, config.getUserAgent());
        assertEquals(pageLoadTimeout, config.getPageLoadTimeout());
        assertEquals(implicitlyWait, config.getImplicitlyWait());
    }

    @Test
    public void testSetterMethods() {
        WebDriverConfig config = new WebDriverConfig();

        config.setWebDriverExecutable("driver.exe");
        config.setUserAgent("TestUserAgent");
        config.setPageLoadTimeout(20L);
        config.setImplicitlyWait(5L);

        assertEquals("driver.exe", config.getWebDriverExecutable());
        assertEquals("TestUserAgent", config.getUserAgent());

        assertEquals(20L, config.getPageLoadTimeout().longValue());
        assertEquals(5L, config.getImplicitlyWait().longValue());
    }

    @Test
    public void testEqualsAndHashCode() {
        WebDriverConfig config1 = new WebDriverConfig("driver.exe", "TestUserAgent", 10L, 5L);
        WebDriverConfig config2 = new WebDriverConfig("driver.exe", "TestUserAgent", 10L, 5L);

        assertEquals(config1, config2);
        assertEquals(config1.hashCode(), config2.hashCode());
    }

    @Test
    public void testToString() {
        WebDriverConfig config = new WebDriverConfig("driver.exe", "TestUserAgent", 15L, 7L);
        String expectedString = "WebDriverConfig{webDriverExecutable='driver.exe', userAgent='TestUserAgent', pageLoadTimeout=15, implicitlyWait=7}";
        assertEquals(expectedString, config.toString());
    }
}