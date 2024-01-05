package executor.service.service.webDriver;

import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.ProxyCredentials;
import executor.service.model.dto.ProxyNetworkConfig;
import executor.service.model.dto.WebDriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

import static java.time.Duration.ofSeconds;

@Component
public class WebDriverInitializerImpl implements WebDriverInitializer {

    @Value("${proxy.extension.url}")
    private String extensionUrl;
    private final WebDriverConfig webDriverConfig;
    private final File[] extensionFiles;

    @Autowired
    public WebDriverInitializerImpl(
            WebDriverConfig webDriverConfig,
            @Qualifier("extensionFile") File[] extensionFiles) {
        this.webDriverConfig = webDriverConfig;
        this.extensionFiles = extensionFiles;
    }

    @Override
    public WebDriver create(ProxyConfigHolder proxyConfigHolder) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions chromeOptions = getChromeOptions();
        configureProxy(chromeOptions, proxyConfigHolder);

        ChromeDriver driver = new ChromeDriver(chromeOptions);

        configureProxyAuth(driver, proxyConfigHolder);
        configureTimeouts(driver);

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

            if (proxyCredentials != null) {
                chromeOptions.addExtensions(extensionFiles);
            }
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
        driver.get(extensionUrl);
        driver.findElement(By.id("url")).sendKeys(".*");
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.className("credential-form-submit")).click();
    }

    private void configureTimeouts(WebDriver driver) {
        driver.manage().timeouts().pageLoadTimeout(ofSeconds(webDriverConfig.getPageLoadTimeout()));
        driver.manage().timeouts().implicitlyWait(ofSeconds(webDriverConfig.getImplicitlyWait()));
    }
}
