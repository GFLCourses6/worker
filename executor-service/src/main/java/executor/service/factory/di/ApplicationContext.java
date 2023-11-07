package executor.service.factory.di;

import executor.service.annotation.Autowired;
import executor.service.annotation.Bean;
import executor.service.annotation.Configuration;
import executor.service.exception.ComponentCreationException;
import executor.service.exception.ConfigComponentCreationException;
import executor.service.exception.ImplCountException;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

import static executor.service.util.ClassUtils.isInterfaceOrAbstractClass;

public class ApplicationContext implements ComponentFactory {

    private final Set<Object> componentHolder;
    private final Reflections reflections;

    public ApplicationContext() {
        this.componentHolder = ConcurrentHashMap.newKeySet();
        this.reflections = new Reflections("executor.service");
    }

    @Override
    public <T> T getComponent(Class<T> tClass) {
        return findInHolder(tClass)
                .orElseGet(() -> create(tClass));
    }

    private <T> T create(Class<T> tClass) {
        T component = findInConfigClasses(tClass)
                .orElseGet(() -> scanPackagesAndCreate(tClass));
        componentHolder.add(component);
        return component;
    }

    private <T> Optional<T> findInHolder(Class<T> tClass) {
        if (isInterfaceOrAbstractClass(tClass)) {
            var implementations = findImplementationsInHolder(tClass).toList();

            if (implementations.size() > 1) {
                throw new ImplCountException("The quantity of the implementations > 1 for "
                        + tClass.getName());
            }
            else if (implementations.size() == 1) {
                return Optional.of(tClass.cast(implementations.get(0)));
            }
            return Optional.empty();
        }
        return findImplementationsInHolder(tClass)
                .findFirst()
                .map(tClass::cast);
    }

    private <T> Stream<Object> findImplementationsInHolder(Class<T> tClass) {
        return componentHolder.stream().filter(tClass::isInstance);
    }

    private <T> Optional<T> findInConfigClasses(Class<T> tClass) {
        List<Method> configImplementations = reflections.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .flatMap(cls -> Arrays.stream(cls.getMethods()))
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .filter(method -> tClass.isAssignableFrom(method.getReturnType()))
                .toList();

        if (configImplementations.size() > 1) {
            throw new ComponentCreationException(
                    "Multiple @Bean found in @Configuration classes for " + tClass.getName());
        }
        else if (configImplementations.size() == 1) {
            try {
                Method method = configImplementations.get(0);
                return Optional.of(tClass.cast(buildFromMethod(method)));
            } catch (Exception e) {
                throw new ConfigComponentCreationException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    private Object buildFromMethod(Method method)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        method.setAccessible(true);
        Class<?> declaringConfigClass = method.getDeclaringClass();
        Object configInstance = declaringConfigClass.getConstructor().newInstance();

        if (method.getParameterCount() == 0) {
            return method.invoke(configInstance);
        }
        Object[] dependencies = createDependencies(method.getParameterTypes());
        return method.invoke(configInstance, dependencies);
    }

    private <T> T scanPackagesAndCreate(Class<T> tClass) {
        Class<? extends T> impl = tClass;
        if (isInterfaceOrAbstractClass(tClass)) {
            impl = scanImpl(tClass);
        }
        return build(impl);
    }

    private <T> T build(Class<? extends T> impl) {
        Constructor<?> constructor = getAutowiredConstructor(impl)
                .orElseGet(() -> getMaxArgsConstructor(impl));

        try {
            if (constructor.getParameterCount() == 0) {
                return impl.cast(constructor.newInstance());
            }
            Object[] dependencies = createDependencies(constructor.getParameterTypes());
            return impl.cast(constructor.newInstance(dependencies));
        }
        catch (Exception e) {
            throw new ComponentCreationException(e.getMessage());
        }
    }

    private <T> Optional<Constructor<?>> getAutowiredConstructor(Class<? extends T> impl) {
        List<Constructor<?>> constructors = Arrays.stream(impl.getConstructors())
                .filter(c -> c.isAnnotationPresent(Autowired.class))
                .toList();

        if (constructors.size() > 1) {
            throw new ComponentCreationException(
                    "Multiple @Autowired found in constructors for " + impl.getName());
        }
        else if (constructors.size() == 1) {
            return Optional.of(constructors.get(0));
        }
        return Optional.empty();
    }

    private <T> Constructor<?> getMaxArgsConstructor(Class<? extends T> impl) {
        return Arrays.stream(impl.getConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new ComponentCreationException("There's no constructor in the" + impl));
    }

    private Object[] createDependencies(Class<?>[] parameters) {
        return Arrays.stream(parameters)
                .map(this::getComponent)
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
                    "The quantity of the implementations is " + implementations.size() +
                            " for " + tClass.getName());
        }
        return implementations.get(0);
    }
}
