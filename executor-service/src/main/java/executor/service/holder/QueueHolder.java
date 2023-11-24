package executor.service.holder;

import java.util.concurrent.BlockingQueue;

public interface QueueHolder<T> {

    BlockingQueue<T> getQueue();
}
