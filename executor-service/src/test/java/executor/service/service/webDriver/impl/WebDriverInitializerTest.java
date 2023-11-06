package executor.service.service.webDriver.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WebDriverInitializerTest {
  private WebDriverInitializer webDriverInitializer;


  @BeforeEach
  public void setUp() {
    WebDriverConfig webDriverConfig = new WebDriverConfig(
        "path/to/webdriver.exe",
        "Mozilla/5.0",
        30L,
        10L
    );

    ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("httpProxyServer", Integer.parseInt("8080"));
    ProxyCredentials proxyCredentials = new ProxyCredentials("proxyUsername", "proxyPassword");
    ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);

    webDriverInitializer = new WebDriverInitializerImpl(webDriverConfig, proxyConfigHolder);
  }


  @Test
  public void testCreateWebDriver() {
    WebDriver driver = webDriverInitializer.create();
    assertNotNull(driver);
  }
}
