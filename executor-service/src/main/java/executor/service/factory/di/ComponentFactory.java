package executor.service.factory.di;

public interface ComponentFactory {

    <T> T getComponent(Class<T> tClass);
}
