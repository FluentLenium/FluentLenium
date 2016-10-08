package org.fluentlenium.utils;

import com.google.common.base.Function;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Utility class for reflection.
 */
@SuppressWarnings("PMD.GodClass")
public final class ReflectionUtils {
    private static final Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    private static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    private static final Map<Class<?>, Class<?>> PRIMITIVES_TO_WRAPPERS = new HashMap<>();
    private static final Map<Class<?>, Object> DEFAULTS = new HashMap<>();
    private static final Map<ClassAnnotationKey, List<Method>> DECLARED_METHODS_CACHE = new WeakHashMap<>();

    private ReflectionUtils() {
        // Utility class
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> wrapPrimitive(final Class<T> clazz) {
        return clazz.isPrimitive() ? (Class<T>) PRIMITIVES_TO_WRAPPERS.get(clazz) : clazz;
    }

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

    static {
        // Only add to this map via put(Map, Class<T>, T)
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

    /**
     * Converts an array of {@code Object} into an array of {@code Class} objects.
     * <p>If any of these objects is null, a null element will be inserted into the array.</p>
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array an {@code Object} array
     * @return a {@code Class} array, {@code null} if null array input
     */
    public static Class<?>[] toClass(final Object... array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_CLASS_ARRAY; // NOPMD MethodReturnsInternalArray
        }
        final Class<?>[] classes = new Class[array.length];
        for (int i = 0; i < array.length; i++) {
            classes[i] = (array[i] == null) ? null : array[i].getClass();
        }
        return classes;
    }

    /**
     * Converts an array of values provided by an array of {@link Class} and {@link Function} supplying value for each
     * class into an array of {@link Object}
     *
     * @param valueSupplier supplier of values for each class
     * @param array         array of class
     * @return array of values
     */
    public static Object[] toArgs(final Function<Class<?>, Object> valueSupplier, final Class<?>... array) {
        if (array == null) {
            return null;
        } else if (array.length == 0) {
            return EMPTY_OBJECT_ARRAY; // NOPMD MethodReturnsInternalArray
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

    public static <T> T getDefault(final Class<T> type) {
        return (T) DEFAULTS.get(type);
    }

    /**
     * Retrieve the constructor of a class for given argument values.
     *
     * @param cls  class to retrieve the constructor from
     * @param args argument values
     * @param <T>  type to retrieve the constructor from
     * @return matching constructor for given argument values
     * @throws NoSuchMethodException if a matching method is not found.
     */
    public static <T> Constructor<T> getConstructor(final Class<T> cls, final Object... args) throws NoSuchMethodException {
        final Class<?>[] argsTypes = toClass(args);
        return getConstructor(cls, argsTypes);
    }

    /**
     * Retrieve the constructor of a class for given argument types.
     *
     * @param cls       class to retrieve the constructor from
     * @param argsTypes argument types
     * @param <T>       type to retrieve the constructor from
     * @return matching constructor for given argument values
     * @throws NoSuchMethodException if a matching method is not found.
     */
    public static <T> Constructor<T> getConstructor(final Class<T> cls, final Class<?>... argsTypes)
            throws NoSuchMethodException {
        if (argsTypes == null || argsTypes.length == 0) {
            return cls.getDeclaredConstructor();
        }

        try {
            return cls.getDeclaredConstructor(argsTypes);
        } catch (final NoSuchMethodException e) {
            for (final Constructor<?> constructor : cls.getDeclaredConstructors()) {
                final Class<?>[] parameterTypes = constructor.getParameterTypes();
                if (parameterTypes.length != argsTypes.length) {
                    continue;
                }

                boolean match = true;
                for (int i = 0; i < parameterTypes.length; i++) {
                    parameterTypes[i] = wrapPrimitive(parameterTypes[i]);
                    if (argsTypes[i] != null) { // NOPMD ConfusingTernary
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

    /**
     * Retrieve the constructor of a class for given optional argument types.
     *
     * @param cls       class to retrieve the constructor from
     * @param argsTypes argument types
     * @param <T>       type to retrieve the constructor from
     * @return matching constructor for given optional argument values
     * @throws NoSuchMethodException if a matching method is not found.
     */
    public static <T> Constructor<T> getConstructorOptional(final Class<T> cls, final Class<?>... argsTypes)
            throws NoSuchMethodException {
        return getConstructorOptional(0, cls, argsTypes);
    }

    /**
     * Retrieve the constructor of a class for given optional argument types, considering mandatory values at the
     * beginning of the given types.
     *
     * @param mandatoryCount number of mandatory arguments at the beginning of the given arguments
     * @param cls            class to retrieve the constructor from
     * @param argsTypes      argument types
     * @param <T>            type to retrieve the constructor from
     * @return matching constructor for given optional argument values
     * @throws NoSuchMethodException if a matching method is not found.
     */
    public static <T> Constructor<T> getConstructorOptional(final int mandatoryCount, final Class<T> cls, Class<?>... argsTypes)
            throws NoSuchMethodException {
        while (true) {
            try {
                return getConstructor(cls, argsTypes);
            } catch (final NoSuchMethodException e) {
                if (argsTypes.length == mandatoryCount) {
                    break;
                }
                argsTypes = Arrays.copyOf(argsTypes, argsTypes.length - 1);
            }
        }
        throw new NoSuchMethodException("Can't find any valid constructor.");
    }

    /**
     * Creates a new instance matching possible constructors with provided args.
     *
     * @param cls  class to instantiate
     * @param args arguments of the constructor
     * @param <T>  type of the instance
     * @return new instance
     * @throws NoSuchMethodException     if a matching method is not found.
     * @throws IllegalAccessException    if this {@code Constructor} object
     *                                   is enforcing Java language access control and the underlying
     *                                   constructor is inaccessible.
     * @throws InstantiationException    if the class that declares the
     *                                   underlying constructor represents an abstract class.
     * @throws InvocationTargetException if the underlying constructor
     *                                   throws an exception.
     */
    public static <T> T newInstance(final Class<T> cls, final Object... args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        final Constructor<T> declaredConstructor = args.length == 0 ? getConstructor(cls) : getConstructor(cls, args);
        final boolean accessible = declaredConstructor.isAccessible();
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
     * @param cls  class to instantiate
     * @param args arguments of the constructor
     * @param <T>  type of the instance
     * @return new instance
     * @throws NoSuchMethodException     if a matching method is not found.
     * @throws IllegalAccessException    if this {@code Constructor} object
     *                                   is enforcing Java language access control and the underlying
     *                                   constructor is inaccessible.
     * @throws InstantiationException    if the class that declares the
     *                                   underlying constructor represents an abstract class.
     * @throws InvocationTargetException if the underlying constructor
     *                                   throws an exception.
     */
    public static <T> T newInstanceOptionalArgs(final Class<T> cls, final Object... args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return newInstanceOptionalArgs(0, cls, args);
    }

    /**
     * Creates a new instance by trying every possible constructors with provided args.
     *
     * @param mandatoryCount count of mandatory arguments
     * @param cls            class to instantiate
     * @param args           arguments of the constructor
     * @param <T>            type of the instance
     * @return new instance
     * @throws NoSuchMethodException     if a matching method is not found.
     * @throws IllegalAccessException    if this {@code Constructor} object
     *                                   is enforcing Java language access control and the underlying
     *                                   constructor is inaccessible.
     * @throws InstantiationException    if the class that declares the
     *                                   underlying constructor represents an abstract class.
     * @throws InvocationTargetException if the underlying constructor
     *                                   throws an exception.
     */
    public static <T> T newInstanceOptionalArgs(final int mandatoryCount, final Class<T> cls, Object... args)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        while (true) {
            try {
                return newInstance(cls, args);
            } catch (final NoSuchMethodException e) {
                if (args.length == mandatoryCount) {
                    break;
                }
                args = Arrays.copyOf(args, args.length - 1);
            }
        }
        throw new NoSuchMethodException("Can't find any valid constructor.");
    }

    public static List<Method> getDeclaredMethodsWithAnnotation(final Object object,
            final Class<? extends Annotation> annotation) {
        if (object == null) {
            return getDeclaredMethodsWithAnnotation(null, annotation);
        }
        return getDeclaredMethodsWithAnnotation(object.getClass(), annotation);
    }

    /**
     * Retrieve declared methods marked with given annotation.
     *
     * @param objectClass class to analyze
     * @param annotation  marker annotation
     * @return Lise of methods that are marked with given annotation
     */
    public static List<Method> getDeclaredMethodsWithAnnotation(final Class<?> objectClass,
            final Class<? extends Annotation> annotation) {
        final List<Method> methods = new ArrayList<>();

        if (objectClass == null) {
            return methods;
        }

        final ClassAnnotationKey cacheKey = new ClassAnnotationKey(objectClass, annotation);
        if (DECLARED_METHODS_CACHE.containsKey(cacheKey)) {
            return DECLARED_METHODS_CACHE.get(cacheKey);
        }

        final Method[] declaredMethods = objectClass.getDeclaredMethods();
        for (final Method method : declaredMethods) {
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
     * @param method method to invoke
     * @param obj    object to invoke
     * @param args   arguments of the method
     * @return return value from the method invocation
     * @throws IllegalAccessException    if this {@code Method} object
     *                                   is enforcing Java language access control and the underlying
     *                                   method is inaccessible.
     * @throws InvocationTargetException if the underlying method
     *                                   throws an exception.
     * @see Method#invoke(Object, Object...)
     */
    public static Object invoke(final Method method, final Object obj, final Object... args)
            throws InvocationTargetException, IllegalAccessException {
        final boolean accessible = method.isAccessible();
        if (accessible) {
            return method.invoke(obj, args);
        }
        method.setAccessible(true);
        try {
            return method.invoke(obj, args);
        } finally {
            method.setAccessible(accessible);
        }
    }

    /**
     * Get the field value even if not accessible.
     *
     * @param field field to get
     * @param obj   instance to get
     * @return field value
     * @throws IllegalAccessException if this {@code Field} object
     *                                is enforcing Java language access control and the underlying
     *                                field is inaccessible.
     * @see Field#get(Object)
     */
    public static Object get(final Field field, final Object obj) throws IllegalAccessException {
        final boolean accessible = field.isAccessible();
        if (accessible) {
            return field.get(obj);
        }
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
     * @param field field to set
     * @param obj   instance to set
     * @param value value of the field to set
     * @throws IllegalAccessException if this {@code Field} object
     *                                is enforcing Java language access control and the underlying
     *                                field is either inaccessible or final.
     * @see Field#set(Object, Object)
     */
    public static void set(final Field field, final Object obj, final Object value) throws IllegalAccessException {
        final boolean accessible = field.isAccessible();
        if (accessible) {
            field.set(obj, value);
        }
        field.setAccessible(true);
        try {
            field.set(obj, value);
        } finally {
            field.setAccessible(accessible);
        }
    }

    /**
     * Retrieve the first generic type of the field type.
     *
     * @param field field to analyze
     * @return first generic type, or null if no generic type is found
     */
    public static Class<?> getFirstGenericType(final Field field) {
        final Type[] actualTypeArguments = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();

        if (actualTypeArguments.length > 0) {
            return (Class<?>) actualTypeArguments[0];
        }

        return null;
    }
}
