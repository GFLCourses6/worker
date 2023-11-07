package executor.service.service.webDriver.impl;

import static java.time.Duration.ofSeconds;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.ProxyCredentials;
import executor.service.model.WebDriverConfig;
import executor.service.service.ConfigPropertiesLoader;
import executor.service.service.webDriver.WebDriverInitializer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

public class WebDriverInitializerImpl implements WebDriverInitializer {

  private final WebDriverConfig webDriverConfig;
  private ProxyConfigHolder proxyConfigHolder;

  public WebDriverInitializerImpl(WebDriverConfig webDriverConfig,
      ProxyConfigHolder proxyConfigHolder) {
    this.webDriverConfig = webDriverConfig;
    this.proxyConfigHolder = proxyConfigHolder;
    loadConfigProperties();
  }

  private void loadConfigProperties() {
    Properties properties = ConfigPropertiesLoader.loadConfigProperties("config.properties");
    webDriverConfig.setUserAgent(properties.getProperty("userAgent"));
    webDriverConfig.setPageLoadTimeout(Long.parseLong(properties.getProperty("pageLoadTimeout")));
    webDriverConfig.setImplicitlyWait(Long.parseLong(properties.getProperty("implicitlyWait")));

    ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig(
        properties.getProperty("proxyHostname"),
        Integer.parseInt(properties.getProperty("proxyPort"))
    );
    ProxyCredentials proxyCredentials = new ProxyCredentials(
        properties.getProperty("proxyUsername"),
        properties.getProperty("proxyPassword")
    );
    proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
  }

  public ChromeOptions getChromeOptions() {
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--user-agent=" + webDriverConfig.getUserAgent());
    chromeOptions.addArguments(generateProxyArguments(proxyConfigHolder));
    return chromeOptions;
  }

  public List<String> generateProxyArguments(ProxyConfigHolder proxyConfigHolder) {
    List<String> arguments = new ArrayList<>();
    ProxyNetworkConfig proxyNetworkConfig = proxyConfigHolder.getProxyNetworkConfig();
    if (proxyNetworkConfig != null) {
      arguments.add("--proxy-hostname=" + proxyNetworkConfig.getHostname());
      arguments.add("--proxy-port=" + proxyNetworkConfig.getPort());
    }

    ProxyCredentials proxyCredentials = proxyConfigHolder.getProxyCredentials();
    if (proxyCredentials != null) {
      arguments.add("--proxy-username=" + proxyCredentials.getUsername());
      arguments.add("--proxy-password=" + proxyCredentials.getPassword());
    }

    return arguments;
  }

  @Override
  public WebDriver create() {
    WebDriverManager.chromedriver().setup();
    WebDriver driver = new ChromeDriver(getChromeOptions());
    driver.manage().timeouts().pageLoadTimeout(ofSeconds(webDriverConfig.getPageLoadTimeout()));
    driver.manage().timeouts().implicitlyWait(ofSeconds(webDriverConfig.getImplicitlyWait()));
    return driver;
  }
}
