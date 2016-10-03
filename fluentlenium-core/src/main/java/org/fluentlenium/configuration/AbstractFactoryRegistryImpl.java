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

public abstract class AbstractFactoryRegistryImpl<T extends Factory, R extends ReflectiveFactory> {
    protected final Class<T> factoryType;
    protected final Class<R> reflectiveFactoryType;

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
            } catch (Exception e) {
                throw new ConfigurationException(factoryClass + " can't be instantiated.", e);
            }
            register(factory);
        }
    }

    protected Map<String, T> factories = new LinkedHashMap<>();

    public synchronized T getDefault() {
        List<T> factories = new ArrayList<>(this.factories.values());
        Collections.sort(factories, new Comparator<T>() {
            @Override
            public int compare(T o1, T o2) {
                FactoryPriority annotation1 = o1.getClass().getAnnotation(FactoryPriority.class);
                int p1 = annotation1 == null ? 0 : annotation1.value();

                FactoryPriority annotation2 = o2.getClass().getAnnotation(FactoryPriority.class);
                int p2 = annotation2 == null ? 0 : annotation2.value();

                return Integer.compare(p2, p1);
            }
        });
        List<T> filteredFactories = new ArrayList<>();
        for (T factory : factories) {
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

    protected abstract T getDefault(List<T> filteredFactories);

    /**
     * Get the factory registered under the given name.
     *
     * @param name name of the factory
     * @return factory
     */
    public synchronized T get(String name) {
        if (name == null) {
            return getDefault();
        }
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
    public synchronized void register(T factory) {
        FactoryName annotation = factory.getClass().getAnnotation(FactoryName.class);
        String annotationName = annotation == null ? null : annotation.value();

        List<String> names = new ArrayList<>();
        if (annotationName != null) {
            names.add(annotationName);
        }
        if (factory instanceof FactoryNames) {
            names.addAll(Arrays.asList(((FactoryNames) factory).getNames()));
        }

        boolean registered = false;

        if (names.size() == 0) {
            throw new ConfigurationException("Factory " + factory.getClass().getName() +
                    " has no name defined. Use @FactoryName annotation or implement FactoryNames.");
        }

        for (String name : names) {
            if (!registered) {
                if (factories.containsKey(name)) {
                    throw new ConfigurationException("A factory is already registered with this name: " +
                            name + " (" + factories.get(name) + ")");
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
