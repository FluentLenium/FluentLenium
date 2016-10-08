package org.fluentlenium.configuration;

import org.atteo.classindex.ClassIndex;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
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
    public AbstractFactoryRegistryImpl(final Class<T> factoryType, final Class<R> reflectiveFactoryType) {
        this.factoryType = factoryType;
        this.reflectiveFactoryType = reflectiveFactoryType;
        final Iterable<Class<? extends T>> factoryClasses = ClassIndex.getSubclasses(factoryType);
        L:
        for (final Class<? extends T> factoryClass : factoryClasses) {
            if (factoryClass.isAnnotationPresent(IndexIgnore.class)) {
                continue;
            }
            for (final Class<?> iface : factoryClass.getInterfaces()) {
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
            final T factory;
            try {
                factory = factoryClass.getConstructor().newInstance();
            } catch (final NoSuchMethodException e) {
                throw new ConfigurationException(factoryClass + " should have a public default constructor.", e);
            } catch (final Exception e) {
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
        final List<T> factoriesList;
        synchronized (this) {
            factoriesList = new ArrayList<>(this.factories.values());
        }
        Collections.sort(factoriesList, new Comparator<T>() {
            @Override
            public int compare(final T o1, final T o2) {
                final FactoryPriority annotation1 = o1.getClass().getAnnotation(FactoryPriority.class);
                final int p1 = annotation1 == null ? 0 : annotation1.value();

                final FactoryPriority annotation2 = o2.getClass().getAnnotation(FactoryPriority.class);
                final int p2 = annotation2 == null ? 0 : annotation2.value();

                return Integer.compare(p2, p1);
            }
        });
        final List<T> filteredFactories = new ArrayList<>();
        for (final T factory : factoriesList) {
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
    public T get(final String name) {
        if (name == null) {
            return getDefault();
        }
        synchronized (this) {
            T factory = factories.get(name);
            if (factory == null) {
                final R reflectiveFactory = newReflectiveInstance(name);
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
    public final void register(final T factory) {
        final FactoryName annotation = factory.getClass().getAnnotation(FactoryName.class);
        final String annotationName = annotation == null ? null : annotation.value();

        final List<String> names = new ArrayList<>();
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

    private void registerImpl(final List<String> names, final T factory) {
        boolean registered = false;
        for (final String name : names) {
            if (!registered) {
                if (factories.containsKey(name)) {
                    throw new ConfigurationException(
                            "A factory is already registered with this name: " + name + " (" + factories.get(name) + ")");
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
