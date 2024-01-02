package executor.service.facade.parallel;

import executor.service.facade.execution.ExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ParallelFlowExecutorServiceImpl implements ParallelFlowExecutorService {

    private final ThreadPoolExecutor configurableThreadPool;
    private final ExecutionService executionService;

    @Autowired
    public ParallelFlowExecutorServiceImpl(ExecutionService executionService,
                                           ThreadPoolExecutor configurableThreadPool) {
        this.executionService = executionService;
        this.configurableThreadPool = configurableThreadPool;
    }

    @Override
    public void execute() {
        if (!configurableThreadPool.isTerminated()) {
            submitTasksToExecutor();
        }
    }

    private void submitTasksToExecutor() {
        for (int i = 0; i < configurableThreadPool.getCorePoolSize(); i++) {
            configurableThreadPool.submit(getWorker());
        }
    }

    private Runnable getWorker() {
        return executionService::execute;
    }
}
