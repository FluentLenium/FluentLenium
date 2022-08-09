package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Capabilities registry default implementation.
 */
public class CapabilitiesRegistryImpl extends AbstractFactoryRegistryImpl<CapabilitiesFactory, ReflectiveCapabilitiesFactory> {
    /**
     * Creates a new capabilities registry.
     */
    public CapabilitiesRegistryImpl() {
        super(CapabilitiesFactory.class, ReflectiveCapabilitiesFactory.class);
        registerDriverCapabilities();
    }

    /**
     * Desired capabilities factory.
     */
    @DefaultFactory
    public static class DriverCapabilitiesFactory extends MethodInvocationReflectionFactory {

        /**
         * Creates a new desired capabilities factory.
         *
         * @param method method to invoke that returns a {@link Capabilities} instance
         */
        public DriverCapabilitiesFactory(Method method) {
            super(method, null);
        }
    }

    private void registerDriverCapabilities() {
        Arrays.stream(PredefinedDesiredCapabilities.class.getMethods())
                .forEach(method -> register(new DriverCapabilitiesFactory(method)));
    }

    @Override
    protected ReflectiveCapabilitiesFactory newReflectiveInstance(String name) {
        return new ReflectiveCapabilitiesFactory(name, name);
    }

    @Override
    protected CapabilitiesFactory getDefault(List<CapabilitiesFactory> filteredFactories) {
        List<CapabilitiesFactory> defaultFactories = new ArrayList<>();
        L:
        for (CapabilitiesFactory factory : filteredFactories) {
            if (factory.getClass().isAnnotationPresent(IndexIgnore.class)) {
                continue;
            }
            for (Class<?> iface : factory.getClass().getInterfaces()) {
                if (iface.isAnnotationPresent(IndexIgnore.class)) {
                    continue L;
                }
            }
            defaultFactories.add(factory);
        }

        if (defaultFactories.isEmpty()) {
            return null;
        }
        return defaultFactories.get(0);
    }

    @Override
    protected void handleNoFactoryAvailable(String name) {
        // Do nothing.
    }

    /**
     * Creates a new {@link CapabilitiesRegistry} instance from factory of the given name
     *
     * @param name          name of the factory used to create new WebDriver instance
     * @param configuration configuration
     * @return a new Capabilities instance
     */
    public Capabilities newCapabilities(String name, ConfigurationProperties configuration) {
        synchronized (this) {
            return get(name).newCapabilities(configuration);
        }
    }
}
