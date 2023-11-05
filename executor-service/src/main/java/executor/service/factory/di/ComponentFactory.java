package executor.service.factory.di;

public interface ComponentFactory {

    <T> T create(Class<T> tClass);
}
