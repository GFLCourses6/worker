package executor.service.service.webDriver.impl;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.model.WebDriverConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class WebDriverInitializerTest {

    @Mock
    private WebDriverConfig webDriverConfig;
    @InjectMocks
    private WebDriverInitializerImpl webDriverInitializer;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        when(webDriverConfig.getUserAgent()).thenReturn("Mozilla/5.0");
        when(webDriverConfig.getPageLoadTimeout()).thenReturn(30L);
        when(webDriverConfig.getImplicitlyWait()).thenReturn(10L);

        WebDriverManager.chromedriver().setup();
    }

    @Test
    public void createWebDriverWithoutProxy() {
        WebDriver driver = webDriverInitializer.create(new ProxyConfigHolder());
        assertNotNull(driver);

        driver.get("https://www.automationtesting.co.uk/buttons.html");
        driver.findElement(By.xpath("//button[@id='btn_one']")).click();
    }

    @Test
    public void createWebDriverWithAuthenticatedProxy() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder(
                new ProxyNetworkConfig("188.74.210.207", 6286),
                new ProxyCredentials("ixfkiyxf", "0v2ypvysubnt")
        );

        WebDriver driver = webDriverInitializer.create(proxyConfigHolder);
        assertNotNull(driver);
        driver.get("https://www.automationtesting.co.uk/buttons.html");
        driver.findElement(By.xpath("//button[@id='btn_one']")).click();
    }
}