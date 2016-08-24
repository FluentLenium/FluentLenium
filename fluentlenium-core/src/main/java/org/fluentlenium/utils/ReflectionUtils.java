package org.fluentlenium.utils;

import com.google.common.base.Function;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;


/**
 * Utility class for reflection.
 */
@UtilityClass
public class ReflectionUtils {
    private Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    @SuppressWarnings("unchecked")
    public <T> Class<T> wrapPrimitive(Class<T> c) {
        return c.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(c) : c;
    }

    private final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<>();

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

    private final Map<Class<?>, Object> DEFAULTS = new HashMap<>();

    static {
        // Only add to this map via put(Map, Class<T>, T)
        Map<Class<?>, Object> map = new HashMap<Class<?>, Object>();
        DEFAULTS.put(boolean.class, false);
        DEFAULTS.put(char.class, '\0');
        DEFAULTS.put(byte.class, (byte) 0);
        DEFAULTS.put(short.class, (short) 0);
        DEFAULTS.put(int.class, 0);
        DEFAULTS.put(long.class, 0L);
        DEFAULTS.put(float.class, 0f);
        DEFAULTS.put(double.class, 0d);
    }

    @Getter
    @Setter
    @EqualsAndHashCode
    @AllArgsConstructor
    private static class ClassAnnotationKey {
        private Class<?> clazz;
        private Class<? extends Annotation> annotation;
    }

    private final Map<ClassAnnotationKey, List<Method>> DECLARED_METHODS_CACHE = new WeakHashMap<>();


    /**
     * <p>Converts an array of {@code Object} in to an array of {@code Class} objects.
     * If any of these objects is null, a null element will be inserted into the array.</p>
     * <p>
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array an {@code Object} array
     * @return a {@code Class} array, {@code null} if null array input
     */
    public Class<?>[] toClass(final Object... array) {
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

    public Object[] toArgs(Function<Class<?>, Object> valueSupplier, final Class<?>... array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_OBJECT_ARRAY;
        }
        final Object[] parameters = new Object[array.length];
        for (int i = 0; i < array.length; i++) {
            Object value = valueSupplier.apply(array[i]);
            if (value == null) {
                value = DEFAULTS.get(array[i]);
            }
            parameters[i] = value;
        }
        return parameters;
    }

    public <T> Constructor<T> getConstructor(Class<T> cls, Object... args) throws NoSuchMethodException {
        Class<?>[] argsTypes = toClass(args);
        return getConstructor(cls, argsTypes);
    }

    public <T> Constructor<T> getConstructor(Class<T> cls, Class<?>... argsTypes) throws NoSuchMethodException {
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
                    if (argsTypes[i] != null) {
                        if (!parameterTypes[i].isAssignableFrom(argsTypes[i])) {
                            match = false;
                            break;
                        }
                    } else if (parameterTypes[i].isPrimitive()) {
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

    public <T> Constructor<T> getConstructorOptional(Class<T> cls, Class<?>... argsTypes) throws NoSuchMethodException {
        return getConstructorOptional(0, cls, argsTypes);
    }

    public <T> Constructor<T> getConstructorOptional(int mandatoryCount, Class<T> cls, Class<?>... argsTypes) throws NoSuchMethodException {
        while (true) {
            try {
                return getConstructor(cls, argsTypes);
            } catch (NoSuchMethodException e) {
                if (argsTypes.length == mandatoryCount) break;
                argsTypes = Arrays.copyOf(argsTypes, argsTypes.length - 1);
            }
        }
        throw new NoSuchMethodException("Can't find any valid constructor.");
    }

    /**
     * Creates a new instance even if constructor is not accessible.
     *
     * @param cls
     * @param args
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public <T> T newInstance(Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> declaredConstructor = args.length == 0 ? getConstructor(cls) : getConstructor(cls, args);
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

    /**
     * Creates a new instance by trying every possible constructors with provided args.
     *
     * @param cls
     * @param args
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public <T> T newInstanceOptionalArgs(Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return newInstanceOptionalArgs(0, cls, args);
    }

    /**
     * Creates a new instance by trying every possible constructors with provided args.
     *
     * @param mandatoryCount
     * @param cls
     * @param args
     * @param <T>
     * @return
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws InstantiationException
     */
    public <T> T newInstanceOptionalArgs(int mandatoryCount, Class<T> cls, Object... args) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        while (true) {
            try {
                return newInstance(cls, args);
            } catch (NoSuchMethodException e) {
                if (args.length == mandatoryCount) break;
                args = Arrays.copyOf(args, args.length - 1);
            }
        }
        throw new NoSuchMethodException("Can't find any valid constructor.");
    }

    public List<Method> getDeclaredMethodsWithAnnotation(Object object, Class<? extends Annotation> annotation) {
        if (object == null) return getDeclaredMethodsWithAnnotation((Class<?>) null, annotation);
        return getDeclaredMethodsWithAnnotation(object.getClass(), annotation);
    }

    public List<Method> getDeclaredMethodsWithAnnotation(Class<?> objectClass, Class<? extends Annotation> annotation) {
        List<Method> methods = new ArrayList<>();

        if (objectClass == null) return methods;

        ClassAnnotationKey cacheKey = new ClassAnnotationKey(objectClass, annotation);
        if (DECLARED_METHODS_CACHE.containsKey(cacheKey)) {
            return DECLARED_METHODS_CACHE.get(cacheKey);
        }

        Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }

        DECLARED_METHODS_CACHE.put(cacheKey, methods);
        return methods;
    }

    /**
     * Invoke the method event if not accessible.
     *
     * @param method
     * @param obj
     * @param args
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @see Method#invoke(Object, Object...)
     */
    public Object invoke(Method method, Object obj, Object... args) throws InvocationTargetException, IllegalAccessException {
        boolean accessible = method.isAccessible();
        if (accessible) return method.invoke(obj, args);
        method.setAccessible(true);
        try {
            return method.invoke(obj, args);
        } finally {
            method.setAccessible(accessible);
        }
    }

    /**
     * Get the field even if not accessible.
     *
     * @param field
     * @param obj
     * @throws IllegalAccessException
     * @see Field#get(Object)
     */
    public Object get(Field field, Object obj) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        if (accessible) return field.get(obj);
        field.setAccessible(true);
        try {
            return field.get(obj);
        } finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * Set the field even if not accessible.
     *
     * @param field
     * @param obj
     * @param value
     * @throws IllegalAccessException
     * @see Field#set(Object, Object)
     */
    public void set(Field field, Object obj, Object value) throws IllegalAccessException {
        boolean accessible = field.isAccessible();
        if (accessible) field.set(obj, value);
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } finally {
            field.setAccessible(accessible);
        }
    }
}
