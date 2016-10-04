package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CapabilitiesRegistryImpl extends AbstractFactoryRegistryImpl<CapabilitiesFactory, ReflectiveCapabilitiesFactory> {
    public CapabilitiesRegistryImpl() {
        super(CapabilitiesFactory.class, ReflectiveCapabilitiesFactory.class);
        registerDesiredCapabilities();
    }

    private void registerDesiredCapabilities() {
        Method[] declaredMethods = DesiredCapabilities.class.getDeclaredMethods();

        for (Method method : declaredMethods) {
            if (Modifier.isStatic(method.getModifiers()) && Capabilities.class.isAssignableFrom(method.getReturnType())) {
                MethodInvocationReflectionFactory factory = new MethodInvocationReflectionFactory(method, null);
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
     * @param name name of the factory used to create new WebDriver instance
     * @return a new Capabilities instance
     */
    public Capabilities newCapabilities(String name) {
        synchronized (this) {
            return get(name).newCapabilities();
        }
    }
}
