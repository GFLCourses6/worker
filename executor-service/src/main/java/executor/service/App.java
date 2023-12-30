package executor.service;

import executor.service.facade.parallel.ParallelFlowExecutorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class App implements CommandLineRunner {

    private final ParallelFlowExecutorService executorService;

    public App(final ParallelFlowExecutorService executorService) {
        this.executorService = executorService;
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) {
        executorService.execute();
    }
}
