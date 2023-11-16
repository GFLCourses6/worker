package executor.service.holder;

import java.util.Queue;

public interface QueueHolder<T> {

    Queue<T> getQueue();
}
