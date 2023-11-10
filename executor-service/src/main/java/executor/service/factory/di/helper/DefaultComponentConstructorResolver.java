package executor.service.factory.di.helper;

import executor.service.annotation.Autowired;
import executor.service.exception.ComponentCreationException;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class DefaultComponentConstructorResolver implements ComponentConstructorResolver {

    @Override
    public <T> Constructor<?> getConstructor(Class<? extends T> impl) {
        return getAutowiredConstructor(impl)
                .orElseGet(() -> getMaxArgsConstructor(impl));
    }

    private <T> Optional<Constructor<?>> getAutowiredConstructor(Class<? extends T> impl) {
        List<Constructor<?>> constructors = Arrays.stream(impl.getConstructors())
                .filter(c -> c.isAnnotationPresent(Autowired.class))
                .toList();

        if (constructors.size() > 1) {
            throw new ComponentCreationException(
                    "Multiple @Autowired found in constructors for " + impl.getName());
        }
        else if (constructors.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(constructors.get(0));
    }

    private <T> Constructor<?> getMaxArgsConstructor(Class<? extends T> impl) {
        return Arrays.stream(impl.getConstructors())
                .max(Comparator.comparing(Constructor::getParameterCount))
                .orElseThrow(() -> new ComponentCreationException("There's no constructor in the" + impl));
    }
}
