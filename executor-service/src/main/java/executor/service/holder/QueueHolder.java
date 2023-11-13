package executor.service.holder;

import java.util.Collection;
import java.util.Optional;

public interface QueueHolder<T> {
    void add(T instance);
    void addAll(Collection<T> instances);
    Optional<T> poll();
}
