package executor.service.factory.di.helper;

import java.lang.reflect.Constructor;

public interface ComponentConstructorResolver {

    <T> Constructor<?> getConstructor(Class<? extends T> impl);
}
