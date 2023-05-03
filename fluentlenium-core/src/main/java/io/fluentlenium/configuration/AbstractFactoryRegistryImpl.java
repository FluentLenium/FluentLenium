package io.fluentlenium.configuration;

import org.atteo.classindex.ClassIndex;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        for (Class<? extends T> factoryClass : factoryClasses) {
            if (!factoryClass.isAnnotationPresent(IndexIgnore.class)
                    && noInterfaceIsAnnotatedAsIndexIgnore(factoryClass)
                    && isNotAbstractAndPublic(factoryClass)) {
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
    }

    private boolean noInterfaceIsAnnotatedAsIndexIgnore(Class<? extends T> factoryClass) {
        return Arrays.stream(factoryClass.getInterfaces())
                .noneMatch(iface -> iface.isAnnotationPresent(IndexIgnore.class));
    }

    private boolean isNotAbstractAndPublic(Class<? extends T> factoryClass) {
        return !Modifier.isAbstract(factoryClass.getModifiers()) && Modifier.isPublic(factoryClass.getModifiers());
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
        factoriesList.sort((factory1, factory2) -> Integer.compare(getPriority(factory2), getPriority(factory1)));

        List<T> filteredFactories = new ArrayList<>();
        factoriesList.stream()
                .filter(factory -> !(factory instanceof ReflectiveFactory) || isActiveReflectiveFactory(factory))
                .forEach(filteredFactories::add);
        return getDefault(filteredFactories);
    }

    private int getPriority(T factory1) {
        FactoryPriority annotation1 = factory1.getClass().getAnnotation(FactoryPriority.class);
        return annotation1 == null ? 0 : annotation1.value();
    }

    private boolean isActiveReflectiveFactory(T factory) {
        return factory instanceof ReflectiveFactory && ((ReflectiveFactory) factory).isAvailable();
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
        if (name != null) {
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
        return getDefault();
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
        List<String> names = new ArrayList<>();
        FactoryName annotation = factory.getClass().getAnnotation(FactoryName.class);
        Optional.ofNullable(annotation).map(FactoryName::value).ifPresent(names::add);

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
                    T existingFactory = factories.get(name);
                    if (!existingFactory.getClass().isAnnotationPresent(DefaultFactory.class)) {
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
