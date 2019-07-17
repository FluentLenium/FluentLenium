package org.fluentlenium.configuration;

import org.atteo.classindex.ClassIndex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Abstract registry of FluentLenium factories.
 *
 * @param <T> type of factories
 * @param <R> type of reflective factories
 */
public abstract class AbstractFactoryRegistryImpl<T extends Factory, R extends ReflectiveFactory> {
    protected final Class<T> factoryType;
    protected final Class<R> reflectiveFactoryType;
    protected Map<String, T> factories = new LinkedHashMap<>();

    /**
     * Creates a new factory registry.
     *
     * @param factoryType           type of factories
     * @param reflectiveFactoryType type of reflective factories
     */
    public AbstractFactoryRegistryImpl(Class<T> factoryType, Class<R> reflectiveFactoryType) {
        this.factoryType = factoryType;
        this.reflectiveFactoryType = reflectiveFactoryType;
        Iterable<Class<? extends T>> factoryClasses = ClassIndex.getSubclasses(factoryType);
        L:
        for (Class<? extends T> factoryClass : factoryClasses) {
            if (factoryClass.isAnnotationPresent(IndexIgnore.class)) {
                continue;
            }
            for (Class<?> iface : factoryClass.getInterfaces()) {
                if (iface.isAnnotationPresent(IndexIgnore.class)) {
                    continue L;
                }
            }
            if (Modifier.isAbstract(factoryClass.getModifiers())) {
                continue;
            }
            if (!Modifier.isPublic(factoryClass.getModifiers())) {
                continue;
            }
            T factory;
            try {
                factory = factoryClass.getConstructor().newInstance();
            } catch (NoSuchMethodException e) {
                throw new ConfigurationException(factoryClass + " should have a public default constructor.", e);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
                throw new ConfigurationException(factoryClass + " can't be instantiated.", e);
            }
            register(factory);
        }
    }

    /**
     * Get the default factory.
     *
     * @return default factory
     */
    public T getDefault() {
        List<T> factoriesList;
        synchronized (this) {
            factoriesList = new ArrayList<>(factories.values());
        }
        factoriesList.sort((o1, o2) -> {
            FactoryPriority annotation1 = o1.getClass().getAnnotation(FactoryPriority.class);
            int p1 = annotation1 == null ? 0 : annotation1.value();

            FactoryPriority annotation2 = o2.getClass().getAnnotation(FactoryPriority.class);
            int p2 = annotation2 == null ? 0 : annotation2.value();

            return Integer.compare(p2, p1);
        });
        List<T> filteredFactories = new ArrayList<>();
        for (T factory : factoriesList) {
            if (factory instanceof ReflectiveFactory) {
                if (((ReflectiveFactory) factory).isAvailable()) {
                    filteredFactories.add(factory);
                }
            } else {
                filteredFactories.add(factory);
            }
        }
        return getDefault(filteredFactories);
    }

    /**
     * Get the default factory from given list of available factories.
     *
     * @param filteredFactories available factories
     * @return default factory
     */
    protected abstract T getDefault(List<T> filteredFactories);

    /**
     * Get the factory registered under the given name.
     *
     * @param name name of the factory
     * @return factory
     */
    public T get(String name) {
        if (name == null) {
            return getDefault();
        }
        synchronized (this) {
            T factory = factories.get(name);
            if (factory == null) {
                R reflectiveFactory = newReflectiveInstance(name);
                if (reflectiveFactory.isAvailable()) {
                    factories.put(name, (T) reflectiveFactory);
                    factory = (T) reflectiveFactory;
                } else {
                    handleNoFactoryAvailable(name);
                }
            }
            return factory;
        }
    }

    /**
     * Handle the case when no factory is available for given name
     *
     * @param name request factory name
     */
    protected abstract void handleNoFactoryAvailable(String name);

    /**
     * Creates an instance of reflective factory.
     *
     * @param name name of the instance to create.
     * @return new instance
     */
    protected abstract R newReflectiveInstance(String name);

    /**
     * Register a new factory.
     * <p>
     * It will use {@link FactoryName} value as the default name.
     * <p>
     * It will also register the factory under names returned by {@link FactoryNames#getNames()}} if
     * it implements {@link FactoryNames}.
     *
     * @param factory factory to register
     */
    public final void register(T factory) {
        FactoryName annotation = factory.getClass().getAnnotation(FactoryName.class);
        String annotationName = annotation == null ? null : annotation.value();

        List<String> names = new ArrayList<>();
        if (annotationName != null) {
            names.add(annotationName);
        }
        if (factory instanceof FactoryNames) {
            names.addAll(Arrays.asList(((FactoryNames) factory).getNames()));
        }

        if (names.isEmpty()) {
            throw new ConfigurationException("Factory " + factory.getClass().getName()
                    + " has no name defined. Use @FactoryName annotation or implement FactoryNames.");
        }

        synchronized (this) {
            registerImpl(names, factory);
        }
    }

    private void registerImpl(List<String> names, T factory) {
        boolean registered = false;
        for (String name : names) {
            if (!registered) {
                if (factories.containsKey(name)) {
                    T exitingFactory = factories.get(name);
                    if (!exitingFactory.getClass().isAnnotationPresent(DefaultFactory.class)) {
                        throw new ConfigurationException(
                                "A factory is already registered with this name: " + name + " (" + factories.get(name) + ")");
                    }
                }
                factories.put(name, factory);
                registered = true;
            }
            if (!factories.containsKey(name)) {
                factories.put(name, factory);
            }
        }
    }
}
