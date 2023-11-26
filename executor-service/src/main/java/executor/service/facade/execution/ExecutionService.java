package executor.service.facade.execution;

import org.openqa.selenium.WebDriver;

import java.util.function.Supplier;

public interface ExecutionService {

    void execute(Supplier<WebDriver> webDriverSupplier);
}
