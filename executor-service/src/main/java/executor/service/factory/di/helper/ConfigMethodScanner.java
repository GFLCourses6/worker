package executor.service.factory.di.helper;

import java.lang.reflect.Method;
import java.util.Optional;

public interface ConfigMethodScanner {

    <T> Optional<Method> scanConfigMethod(Class<T> tClass);
}
