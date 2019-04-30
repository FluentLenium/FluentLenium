package org.fluentlenium.configuration;

import org.apache.commons.lang3.ArrayUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
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
        registerDesiredCapabilities();
    }

    /**
     * Desired capabilities factory.
     */
    @DefaultFactory
    public static class DesiredCapabilitiesFactory extends MethodInvocationReflectionFactory {

        /**
         * Creates a new desired capabilities factory.
         *
         * @param method method to invoke that returns a {@link Capabilities} instance
         */
        public DesiredCapabilitiesFactory(Method method) {
            super(method, null);
        }
    }

    private void registerDesiredCapabilities() {
        Method[] methodsDeclaredInSelenium = DesiredCapabilities.class.getDeclaredMethods();
        Method[] predefinedDeclaredMethods = PredefinedDesiredCapabilities.class.getDeclaredMethods();

        Method[] declaredMethods = ArrayUtils.addAll(methodsDeclaredInSelenium, predefinedDeclaredMethods);

        for (Method method : declaredMethods) {
            if (Modifier.isStatic(method.getModifiers()) && Capabilities.class.isAssignableFrom(method.getReturnType())) {
                DesiredCapabilitiesFactory factory = new DesiredCapabilitiesFactory(method);
                register(factory);
            }
        }
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
