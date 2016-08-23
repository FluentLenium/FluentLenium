package org.fluentlenium.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;


/**
 * Utility class for reflection.
 */
public abstract class ReflectionUtils {
    private static Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];

    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrapPrimitive(Class<T> c) {
        return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
    }

    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<>();

    static {
        PRIMITIVES_TO_WRAPPERS.put(boolean.class, Boolean.class);
        PRIMITIVES_TO_WRAPPERS.put(byte.class, Byte.class);
        PRIMITIVES_TO_WRAPPERS.put(char.class, Character.class);
        PRIMITIVES_TO_WRAPPERS.put(double.class, Double.class);
        PRIMITIVES_TO_WRAPPERS.put(float.class, Float.class);
        PRIMITIVES_TO_WRAPPERS.put(int.class, Integer.class);
        PRIMITIVES_TO_WRAPPERS.put(long.class, Long.class);
        PRIMITIVES_TO_WRAPPERS.put(short.class, Short.class);
        PRIMITIVES_TO_WRAPPERS.put(void.class, Void.class);
    }


    /**
     * <p>Converts an array of {@code Object} in to an array of {@code Class} objects.
     * If any of these objects is null, a null element will be inserted into the array.</p>
     * <p>
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array an {@code Object} array
     * @return a {@code Class} array, {@code null} if null array input
     */
    public static Class<?>[] toClass(final Object... array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CLASS_ARRAY;
        }
        final Class<?>[] classes = new Class[array.length];
        for (int i = 0; i < array.length; i++) {
            classes[i] = (array[i] == null) ? null : array[i].getClass();
        }
        return classes;
    }

    public static <T> Constructor<T> getConstructor(Class<T> cls, Object... args) throws NoSuchMethodException {
        Class<?>[] argsTypes = toClass(args);
        return getConstructor(cls, argsTypes);
    }

    public static <T> Constructor<T> getConstructor(Class<T> cls, Class<?>... argsTypes) throws NoSuchMethodException {
        if (argsTypes == null || argsTypes.length == 0) {
            return cls.getDeclaredConstructor();
        }

        try {
            return cls.getDeclaredConstructor(argsTypes);
        } catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : cls.getDeclaredConstructors()) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length != argsTypes.length) continue;

                boolean match = true;
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameterTypes[i] = wrapPrimitive(parameterTypes[i]);
                    if (!parameterTypes[i].isAssignableFrom(argsTypes[i])) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return (Constructor<T>) constructor;
                }
            }

            throw e;
        }
    }

    public static <T> T newInstance(Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> declaredConstructor = getConstructor(cls, args);
        boolean accessible = declaredConstructor.isAccessible();
        if (accessible) {
            return declaredConstructor.newInstance(args);
        } else {
            declaredConstructor.setAccessible(true);
            try {
                return declaredConstructor.newInstance(args);
            } finally {
                declaredConstructor.setAccessible(accessible);
            }
        }
    }
}
