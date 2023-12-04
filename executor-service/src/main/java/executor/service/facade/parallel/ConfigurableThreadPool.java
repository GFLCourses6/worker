package executor.service.facade.parallel;

import executor.service.model.ThreadPoolConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

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
        logger.trace(String.format("Thread %s: start %s", t, r));
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        try {
            long endTime = System.nanoTime();
            long taskTime = endTime - startTime.get();
            numTasks.incrementAndGet();
            totalTime.addAndGet(taskTime);
            logger.trace(String.format("Thread %s: end %s, time=%dns",
                    t, r, taskTime));
        } finally {
            super.afterExecute(r, t);
        }
    }

    @Override
    protected void terminated() {
        try {
            logger.trace(String.format("Terminated: avg time=%dns",
                    totalTime.get() / numTasks.get()));
        } finally {
            super.terminated();
        }
    }
}
