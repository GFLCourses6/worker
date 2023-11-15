package executor.service.holder;

import java.util.Collection;
import java.util.Optional;
import java.util.Queue;

public abstract class AbstractQueueHolder<T> implements QueueHolder<T> {

    abstract Queue<T> getQueue();

    @Override
    public void add(T queueComponent) {
        getQueue().add(queueComponent);
    }

    @Override
    public void addAll(Collection<T> queueComponents) {
        getQueue().addAll(queueComponents);
    }

    @Override
    public Optional<T> poll() {
        return Optional.ofNullable(getQueue().poll());
    }

    @Override
    public boolean isEmpty() {
        return getQueue().isEmpty();
    }
}
