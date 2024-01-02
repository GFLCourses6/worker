package executor.service.service.webDriver.impl;

import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.ProxyCredentials;
import executor.service.model.dto.ProxyNetworkConfig;
import executor.service.model.dto.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializerImpl;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class WebDriverInitializerTest {

    @Mock
    private WebDriverConfig webDriverConfig;
    @InjectMocks
    private WebDriverInitializerImpl webDriverInitializer;
    private AutoCloseable openMocks;

    @BeforeEach
    void setUp() {
        openMocks = MockitoAnnotations.openMocks(this);

        when(webDriverConfig.getUserAgent()).thenReturn("Mozilla/5.0");
        when(webDriverConfig.getPageLoadTimeout()).thenReturn(30L);
        when(webDriverConfig.getImplicitlyWait()).thenReturn(10L);

        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    void createWebDriverWithoutProxy() {
        WebDriver driver = webDriverInitializer.create(new ProxyConfigHolder());
        assertNotNull(driver);

        driver.get("https://www.automationtesting.co.uk/buttons.html");
        driver.findElement(By.xpath("//button[@id='btn_one']")).click();
    }

    void createWebDriverWithAuthenticatedProxy() {
        ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder(
                new ProxyNetworkConfig("193.42.225.12", 6503),
                new ProxyCredentials("ixfkiyxf", "0v2ypvysubnt")
        );

        WebDriver driver = webDriverInitializer.create(proxyConfigHolder);
        assertNotNull(driver);
        driver.get("https://www.automationtesting.co.uk/buttons.html");
        driver.findElement(By.xpath("//button[@id='btn_one']")).click();
    }
}
