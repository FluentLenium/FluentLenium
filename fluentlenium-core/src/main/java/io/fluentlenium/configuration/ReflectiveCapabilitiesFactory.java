package io.fluentlenium.configuration;

import io.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link CapabilitiesFactory} that create {@link Capabilities} instances using reflection.
 */
public class ReflectiveCapabilitiesFactory implements CapabilitiesFactory, FactoryNames, ReflectiveFactory {
    private final String name;
    private final Object[] args;
    private final String capabilitiesClassName;
    private Class<? extends Capabilities> capabilitiesClass;
    private boolean available;

    /**
     * Creates a new reflective capabilities factory.
     *
     * @param name                  factory name
     * @param capabilitiesClassName capabilities class name
     * @param args                  capabilities class constructor arguments
     */
    public ReflectiveCapabilitiesFactory(String name, String capabilitiesClassName, Object... args) {
        this.name = name;
        this.capabilitiesClassName = capabilitiesClassName;
        this.args = args;
        try {
            capabilitiesClass = (Class<? extends Capabilities>) Class.forName(capabilitiesClassName);
            available = Capabilities.class.isAssignableFrom(capabilitiesClass);
        } catch (ClassNotFoundException e) {
            available = false;
        }
    }

    /**
     * Creates a new reflective capabilities factory.
     *
     * @param name              factory name
     * @param capabilitiesClass capabilities class
     * @param args              capabilities class constructor arguments
     */
    public ReflectiveCapabilitiesFactory(String name, Class<? extends Capabilities> capabilitiesClass, Object... args) {
        this.name = name;
        this.capabilitiesClass = capabilitiesClass;
        this.args = args;
        capabilitiesClassName = capabilitiesClass.getName();
        available = Capabilities.class.isAssignableFrom(this.capabilitiesClass);
    }

    /**
     * Get the capabilities class
     *
     * @return capabilities class
     */
    public Class<? extends Capabilities> getCapabilitiesClass() {
        return capabilitiesClass;
    }

    @Override
    public boolean isAvailable() {
        return available;
    }

    /**
     * Creates default capabilities.
     *
     * @return default capabilities
     */
    protected DesiredCapabilities newDefaultCapabilities() {
        return null;
    }

    @Override
    public Capabilities newCapabilities(ConfigurationProperties configuration) {
        if (!available) {
            throw new ConfigurationException("Capabilities " + capabilitiesClassName + " is not available.");
        }

        try {
            return ReflectionUtils.newInstance(capabilitiesClass, args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new ConfigurationException("Can't create new Capabilities instance", e);
        }
    }

    @Override
    public String[] getNames() {
        List<String> names = new ArrayList<>(Arrays.asList(name));
        if (capabilitiesClass != null) {
            names.add(capabilitiesClass.getName());
            names.add(capabilitiesClass.getSimpleName());
        }
        return names.toArray(new String[names.size()]);
    }
}
