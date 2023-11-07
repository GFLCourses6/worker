package executor.service.service.webDriver.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializer;
import java.time.Duration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverInitializerImpl implements WebDriverInitializer {
  private final WebDriverConfig webDriverConfig;

  public WebDriverInitializerImpl(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
    this.webDriverConfig = webDriverConfig;
  }

  @Override
  public WebDriver create() {
    System.setProperty("config.properties", webDriverConfig.getWebDriverExecutable());
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--user-agent=" + webDriverConfig.getUserAgent());
    WebDriver driver = new ChromeDriver(chromeOptions);
    Duration pageLoadTimeout = Duration.ofSeconds(webDriverConfig.getPageLoadTimeout());
    driver.manage().timeouts().pageLoadTimeout(pageLoadTimeout);
    Duration implicitlyWait = Duration.ofSeconds(webDriverConfig.getImplicitlyWait());
    driver.manage().timeouts().implicitlyWait(implicitlyWait);
    return driver;
  }
}
