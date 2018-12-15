package org.fluentlenium.configuration;

import org.openqa.selenium.Capabilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * {@link CapabilitiesFactory} based on invocation of a defined method.
 */
@IndexIgnore
public class MethodInvocationReflectionFactory implements CapabilitiesFactory, FactoryNames {
    private final Method method;
    private final Object instance;
    private final Object[] args;

    /**
     * Creates a new method invocation reflection factory.
     *
     * @param method   method to invoke that returns a {@link Capabilities} instance
     * @param instance instance to use
     * @param args     arguments to pass
     */
    public MethodInvocationReflectionFactory(Method method, Object instance, Object... args) {
        this.method = method;
        this.instance = instance;
        this.args = args;
    }

    @Override
    public Capabilities newCapabilities(ConfigurationProperties configuration) {
        try {
            return (Capabilities) method.invoke(instance, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new ConfigurationException("Can't create capabilities instance", e);
        }
    }

    @Override
    public String[] getNames() {
        return new String[] {method.getDeclaringClass().getName() + "." + method.getName(),
                method.getDeclaringClass().getSimpleName() + "." + method.getName(), method.getName()};
    }
}
