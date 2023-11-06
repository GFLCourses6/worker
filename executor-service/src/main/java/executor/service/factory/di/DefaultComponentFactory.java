package executor.service.factory.di;

import executor.service.exception.ComponentCreationException;
import executor.service.exception.ImplCountException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class DefaultComponentFactory implements ComponentFactory {

    private final Set<Object> componentHolder;
    private final Reflections reflections;


    public DefaultComponentFactory(Set<Object> componentHolder) {
        this.componentHolder = componentHolder;
        this.reflections = new Reflections("executor.service");
    }

    @Override
    public <T> T create(Class<T> tClass) {
        T instance = findInHolder(tClass);
        if (instance != null) {
            return instance;
        }
        instance = scanPackagesAndCreate(tClass);
        componentHolder.add(instance);
        return instance;
    }

    private <T> T findInHolder(Class<T> tClass) {
        if (isInterfaceOrAbstractClass(tClass)) {
            var implementations = findImplementationsInHolder(tClass).toList();

            if (implementations.size() > 1) {
                throw new ImplCountException("The quantity of the implementations > 1 for " + tClass);
            }
            else if (implementations.size() == 1) {
                return tClass.cast(implementations.get(0));
            }
            return null;
        }
        return tClass.cast(findImplementationsInHolder(tClass).findFirst().get());
    }

    private <T> Stream<? extends Class<?>> findImplementationsInHolder(Class<T> tClass) {
        return componentHolder.stream()
                .map(Object::getClass)
                .filter(tClass::isAssignableFrom);
    }

    private <T> T scanPackagesAndCreate(Class<T> tClass) {
        Class<? extends T> impl = tClass;
        if (isInterfaceOrAbstractClass(tClass)) {
            impl = scanImpl(tClass);
        }
        return buildComponent(impl);
    }

    private <T> T buildComponent(Class<? extends T> impl) {
        Constructor<?> constructor = Arrays.stream(impl.getConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new IllegalArgumentException("There's no constructor in the" + impl));

        try {
            if (constructor.getParameterCount() == 0) {
                return impl.cast(constructor.newInstance());
            }
            Object[] dependencies = createDependencies(constructor);
            return impl.cast(constructor.newInstance(dependencies));
        }
        catch (Exception e) {
            throw new ComponentCreationException(e.getMessage());
        }
    }

    private Object[] createDependencies(Constructor<?> constructor) {
        return Arrays.stream(constructor.getParameterTypes())
                .map(this::create)
                .toArray();
    }

    private <T> Class<? extends T> scanImpl(Class<T> tClass) {
        List<Class<? extends T>> implementations = reflections.getSubTypesOf(tClass)
                .stream()
                .filter(c -> !c.isInterface())
                .filter(c -> !Modifier.isAbstract(c.getModifiers()))
                .toList();

        if (implementations.size() != 1) {
            throw new ImplCountException(
                    "The quantity of the implementations is " + implementations.size() + " for " + tClass
            );
        }
        return implementations.get(0);
    }

    private <T> boolean isInterfaceOrAbstractClass(Class<T> tClass) {
        return tClass.isInterface() || Modifier.isAbstract(tClass.getModifiers());
    }
}
