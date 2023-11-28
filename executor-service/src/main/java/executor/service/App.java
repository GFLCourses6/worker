package executor.service;

import executor.service.facade.parallel.ParallelFlowExecutorService;
import executor.service.factory.di.ApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ApplicationContext();
        ParallelFlowExecutorService executorService = applicationContext.getComponent(ParallelFlowExecutorService.class);
        executorService.execute();
    }
}
