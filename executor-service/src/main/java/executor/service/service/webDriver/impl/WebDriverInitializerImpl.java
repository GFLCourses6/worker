package executor.service.service.webDriver.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializer;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebDriverInitializerImpl implements WebDriverInitializer {
  private final WebDriverConfig webDriverConfig;
  private final ProxyConfigHolder proxyConfigHolder;

  public WebDriverInitializerImpl(WebDriverConfig webDriverConfig, ProxyConfigHolder proxyConfigHolder) {
    this.webDriverConfig = webDriverConfig;
    this.proxyConfigHolder = proxyConfigHolder;
  }

  @Override
  public WebDriver create() {
    System.setProperty("config.properties", webDriverConfig.getWebDriverExecutable());
    ChromeOptions chromeOptions = new ChromeOptions();
    chromeOptions.addArguments("--user-agent=" + webDriverConfig.getUserAgent());
    WebDriver driver = new ChromeDriver(chromeOptions);
    driver.manage().timeouts().pageLoadTimeout(webDriverConfig.getPageLoadTimeout(), TimeUnit.SECONDS);
    driver.manage().timeouts().implicitlyWait(webDriverConfig.getImplicitlyWait(), TimeUnit.SECONDS);
    return driver;
  }
}
