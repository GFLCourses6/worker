package executor.service.service.webDriver.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializer;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

import static java.time.Duration.ofSeconds;

public class WebDriverInitializerImpl implements WebDriverInitializer {

    private static final String EXTENSION_PATH = "src/main/resources/MultiPass-for-HTTP-basic-authentication.crx";
    private static final String EXTENSION_URL = "chrome-extension://enhldmjbphoeibbpdhmjkchohnidgnah/options.html";
    private final WebDriverConfig webDriverConfig;

    public WebDriverInitializerImpl(WebDriverConfig webDriverConfig) {
        this.webDriverConfig = webDriverConfig;
    }

    @Override
    public WebDriver create(ProxyConfigHolder proxyConfigHolder) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = getChromeOptions();
        configureProxy(chromeOptions, proxyConfigHolder);

        ChromeDriver driver = new ChromeDriver(chromeOptions);
        configureProxyAuth(driver, proxyConfigHolder);

        driver.manage().timeouts().pageLoadTimeout(ofSeconds(webDriverConfig.getPageLoadTimeout()));
        driver.manage().timeouts().implicitlyWait(ofSeconds(webDriverConfig.getImplicitlyWait()));

        return driver;
    }

    private ChromeOptions getChromeOptions() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--user-agent=" + webDriverConfig.getUserAgent());
        chromeOptions.setAcceptInsecureCerts(true);

        return chromeOptions;
    }

    private void configureProxy(ChromeOptions chromeOptions, ProxyConfigHolder proxyConfigHolder) {
        ProxyNetworkConfig networkConfig = proxyConfigHolder.getProxyNetworkConfig();
        ProxyCredentials proxyCredentials = proxyConfigHolder.getProxyCredentials();

        if (networkConfig != null) {
            String hostname = networkConfig.getHostname();
            Integer port = networkConfig.getPort();
            chromeOptions.addArguments(String.format("--proxy-server=%s:%d", hostname, port));
        }
        if (networkConfig != null && proxyCredentials != null) {
            chromeOptions.addExtensions(new File(EXTENSION_PATH));
        }
    }

    private void configureProxyAuth(WebDriver driver, ProxyConfigHolder proxyConfigHolder) {
        ProxyNetworkConfig networkConfig = proxyConfigHolder.getProxyNetworkConfig();
        ProxyCredentials proxyCredentials = proxyConfigHolder.getProxyCredentials();

        if (networkConfig != null && proxyCredentials != null) {
            configureAuth(driver, proxyCredentials.getUsername(), proxyCredentials.getPassword());
        }
    }

    private void configureAuth(WebDriver driver, String username, String password) {
        driver.get(EXTENSION_URL);
        driver.findElement(By.id("url")).sendKeys(".*");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("credential-form-submit")).click();
    }
}
