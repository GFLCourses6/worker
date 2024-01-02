package executor.service.facade.parallel;

import executor.service.model.dto.ThreadPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Component;

@Component
public class ConfigurableThreadPool extends ThreadPoolExecutor {

    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    private final Logger logger = LogManager.getLogger(ConfigurableThreadPool.class);
    private final AtomicLong numTasks = new AtomicLong();
    private final AtomicLong totalTime = new AtomicLong();

    public ConfigurableThreadPool(ThreadPoolConfig threadPoolConfig) {
        super(threadPoolConfig.getCorePoolSize(), threadPoolConfig.getCorePoolSize(),
                threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        logger.trace("Thread {}: start {}", t, r);
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            logger.trace("Thread {}: end {}, time={}ns",
                         t, r, taskTime);
        } finally {
            startTime.remove();
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            logger.trace("Terminated: avg time={}ns",
                         totalTime.get() / numTasks.get());
        } finally {
            super.terminated();
        }
    }
}
