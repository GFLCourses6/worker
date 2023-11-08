package executor.service.factory.di.helper;

import java.util.Optional;

public interface PackageComponentScanner {

    <T> Optional<Class<? extends T>> scanImplementation(Class<T> tClass);
}
