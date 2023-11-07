package executor.service.service.webDriver.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class WebDriverInitializerTest {

  private WebDriverInitializer webDriverInitializer;
  private ProxyConfigHolder proxyConfigHolder;

  @BeforeEach
  public void setUp() {
    // Инициализация настроек WebDriver и прокси
    WebDriverConfig webDriverConfig = new WebDriverConfig();
    proxyConfigHolder = new ProxyConfigHolder(
        new ProxyNetworkConfig("proxy.example.com", 8080),
        new ProxyCredentials("username", "password")
    );

    // Используем WebDriverManager для установки и настройки ChromeDriver
    WebDriverManager.chromedriver().setup();

    // Создаем экземпляр WebDriverInitializerImpl
    webDriverInitializer = new WebDriverInitializerImpl(webDriverConfig, proxyConfigHolder);
  }

  @Test
  public void createWebDriverWithoutProxy() {
    // Создаем WebDriver без прокси
    WebDriver driver = webDriverInitializer.create();
    assertNotNull(driver);

    // Ваш код для выполнения действий с WebDriver без прокси
     driver.get("https://www.example.com");
  }

  @Test
  public void createWebDriverWithProxy() {
    // Изменяем настройки прокси для теста с прокси
    proxyConfigHolder = new ProxyConfigHolder(
        new ProxyNetworkConfig("proxy.example.com", 8080),
        new ProxyCredentials("username", "password")
    );

    // Создаем WebDriver с прокси
    WebDriver driver = webDriverInitializer.create();
    assertNotNull(driver);
    driver.get("https://www.example.com");
    // Ваш код для выполнения действий с WebDriver с прокси
  }
}
