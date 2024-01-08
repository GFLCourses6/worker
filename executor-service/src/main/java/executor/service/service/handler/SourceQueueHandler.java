package executor.service.service.handler;

public interface SourceQueueHandler<T> {
    void enqueue(T source);

    T dequeue();

    boolean isQueueEmpty();

    int getQueueSize();
}
