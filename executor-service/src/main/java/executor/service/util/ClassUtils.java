package executor.service.util;

import java.lang.reflect.Modifier;

public class ClassUtils {

    public static <T> boolean isInterfaceOrAbstractClass(Class<T> tClass) {
        return tClass.isInterface() || Modifier.isAbstract(tClass.getModifiers());
    }
}
