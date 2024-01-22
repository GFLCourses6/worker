package executor.service.service.webDriver.impl;

import executor.service.model.dto.ProxyConfigHolder;
import executor.service.model.dto.ProxyCredentials;
import executor.service.model.dto.ProxyNetworkConfig;
import executor.service.model.dto.WebDriverConfig;
import executor.service.service.webDriver.WebDriverInitializerImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WebDriverInitializerImplTest {

    @Mock
    private WebDriverConfig mockWebDriverConfig;

    @Mock
    private File mockExtensionFiles;

    @Mock
    private ProxyConfigHolder mockProxyConfigHolder;

    @Mock
    private ProxyNetworkConfig mockProxyNetworkConfig;

    @Mock
    private ProxyCredentials mockProxyCredentials;

    @Mock
    private ChromeDriver mockChromeDriver;

    @InjectMocks
    private WebDriverInitializerImpl webDriverInitializer;

    @Test
    void createWebDriverWithoutProxy() {
        when(mockProxyConfigHolder.getProxyNetworkConfig()).thenReturn(null);

        WebDriver result = webDriverInitializer.create(mockProxyConfigHolder);

        assertThat(result).isInstanceOf(ChromeDriver.class);
        result.quit();
    }

    @Test
    void createWebDriverWithUnauthenticatedProxy() {
        when(mockProxyConfigHolder.getProxyNetworkConfig()).thenReturn(mockProxyNetworkConfig);
        when(mockProxyConfigHolder.getProxyCredentials()).thenReturn(null);
        when(mockProxyNetworkConfig.getHostname()).thenReturn("proxyHostname");
        when(mockProxyNetworkConfig.getPort()).thenReturn(8080);

        WebDriver result = webDriverInitializer.create(mockProxyConfigHolder);

        assertThat(result).isInstanceOf(ChromeDriver.class);
        result.quit();
    }
}