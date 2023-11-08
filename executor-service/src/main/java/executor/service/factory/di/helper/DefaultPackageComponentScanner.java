package executor.service.factory.di.helper;

import executor.service.exception.ImplCountException;
import org.reflections.Reflections;

import java.lang.reflect.Modifier;
import java.util.List;
import java.util.Optional;

public class DefaultPackageComponentScanner implements PackageComponentScanner {

    private final Reflections reflections;

    public DefaultPackageComponentScanner() {
        this.reflections = new Reflections("executor.service");
    }

    @Override
    public <T> Optional<Class<? extends T>> scanImplementation(Class<T> tClass) {
        List<Class<? extends T>> implementations = reflections.getSubTypesOf(tClass)
                .stream()
                .filter(c -> !c.isInterface())
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .toList();

        if (implementations.size() > 1) {
            throw new ImplCountException(tClass);
        }
        else if (implementations.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(implementations.get(0));
    }
}
