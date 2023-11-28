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
            Optional<Class<? extends T>> cls = findClassExtendsAll(implementations);
            if (cls.isPresent()) {
                return cls;
            }
            throw new ImplCountException(tClass);
        }
        else if (implementations.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(implementations.get(0));
    }

    private <T> Optional<Class<? extends T>> findClassExtendsAll(List<Class<? extends T>> implementations) {
        // find the class which extends (is assignable from) all the classes from the list, and return it
        return implementations.stream()
                .filter(candidateSubclass -> implementations.stream().allMatch(
                        superclass -> superclass.isAssignableFrom(candidateSubclass))
                ).findFirst();
    }
}
