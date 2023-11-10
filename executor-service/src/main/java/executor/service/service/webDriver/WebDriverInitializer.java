package executor.service.service.webDriver;

import executor.service.model.ProxyConfigHolder;
import org.openqa.selenium.WebDriver;

public interface WebDriverInitializer {
    WebDriver create(ProxyConfigHolder proxyConfigHolder);
}
