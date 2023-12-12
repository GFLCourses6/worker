package executor.service;

import executor.service.facade.parallel.ParallelFlowExecutorService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(App.class);
        ParallelFlowExecutorService executorService = applicationContext.getBean(ParallelFlowExecutorService.class);
        executorService.execute();
    }
}
