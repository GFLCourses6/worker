package executor.service.factory.di;

import executor.service.exception.ComponentCreationException;
import executor.service.exception.ConfigComponentCreationException;
import executor.service.exception.ImplCountException;
import executor.service.factory.di.helper.ComponentConstructorResolver;
import executor.service.factory.di.helper.ConfigMethodScanner;
import executor.service.factory.di.helper.PackageComponentScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static executor.service.util.ClassUtils.isInterfaceOrAbstractClass;

public class ApplicationContext implements ComponentFactory {

    private final Set<Object> componentHolder;
    private final ComponentConstructorResolver constructorResolver;
    private final ConfigMethodScanner configMethodScanner;
    private final PackageComponentScanner packageComponentScanner;

    public ApplicationContext(ComponentConstructorResolver constructorResolver,
                              ConfigMethodScanner configMethodScanner,
                              PackageComponentScanner packageComponentScanner) {
        this.constructorResolver = constructorResolver;
        this.configMethodScanner = configMethodScanner;
        this.packageComponentScanner = packageComponentScanner;
        this.componentHolder = ConcurrentHashMap.newKeySet();
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
        var implementations = componentHolder.stream()
                .filter(tClass::isInstance)
                .map(tClass::cast)
                .toList();

        if (implementations.size() > 1) {
            throw new ImplCountException(tClass);
        }
        else if (implementations.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(implementations.get(0));
    }

    private <T> Optional<T> findInConfigClasses(Class<T> tClass) {
        return configMethodScanner.scanConfigMethod(tClass)
                .map(method -> {
                    try {
                        return buildFromMethod(method, tClass);
                    } catch (Exception e) {
                        throw new ConfigComponentCreationException(e.getMessage());
                    }
                });
    }

    private <T> T scanPackagesAndCreate(Class<T> tClass) {
        Class<? extends T> impl = tClass;
        if (isInterfaceOrAbstractClass(tClass)) {
            impl = packageComponentScanner.scanImplementation(tClass)
                    .orElseThrow(() -> new ImplCountException(tClass));
        }
        return build(impl);
    }

    private <T> T buildFromMethod(Method method, Class<T> cls)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {

        method.setAccessible(true);
        Class<?> declaringConfigClass = method.getDeclaringClass();
        Object configInstance = declaringConfigClass.getConstructor().newInstance();

        if (method.getParameterCount() == 0) {
            return cls.cast(method.invoke(configInstance));
        }
        Object[] dependencies = createDependencies(method.getParameterTypes());
        return cls.cast(method.invoke(configInstance, dependencies));
    }

    private <T> T build(Class<? extends T> impl) {
        Constructor<?> constructor = constructorResolver.getConstructor(impl);
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

    private Object[] createDependencies(Class<?>[] parameters) {
        return Arrays.stream(parameters)
                .map(this::getComponent)
                .toArray();
    }
}
