package executor.service.factory.di.helper;

import executor.service.annotation.Bean;
import executor.service.annotation.Configuration;
import executor.service.exception.ComponentCreationException;
import org.reflections.Reflections;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class DefaultConfigMethodScanner implements ConfigMethodScanner {

    private final Reflections reflections;

    public DefaultConfigMethodScanner() {
        this.reflections = new Reflections("executor.service");
    }

    @Override
    public <T> Optional<Method> scanConfigMethod(Class<T> tClass) {
        List<Method> methodList = reflections.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .flatMap(cls -> Arrays.stream(cls.getMethods()))
                .filter(method -> method.isAnnotationPresent(Bean.class))
                .filter(method -> tClass.isAssignableFrom(method.getReturnType()))
                .toList();

        if (methodList.size() > 1) {
            throw new ComponentCreationException(
                    "Multiple @Bean found in @Configuration classes for " + tClass.getName());
        }
        else if (methodList.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(methodList.get(0));
    }
}
