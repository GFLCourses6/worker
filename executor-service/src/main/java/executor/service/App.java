package executor.service;

import executor.service.facade.parallel.ParallelFlowExecutorService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootApplication
public class App implements CommandLineRunner {
    @Autowired
    private ParallelFlowExecutorService executorService;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        executorService.execute();
    }
}
