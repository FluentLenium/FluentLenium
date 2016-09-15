package org.fluentlenium.configuration;


import org.fluentlenium.utils.ReflectionUtils;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.lang.reflect.InvocationTargetException;

/**
 * A simple {@link CapabilitiesFactory} that create {@link Capabilities} instances using reflection.
 */
public class ReflectiveCapabilitiesFactory implements CapabilitiesFactory, AlternativeNames, ReflectiveFactory {
    private String name;
    private Object[] args;
    private String capabilitiesClassName;
    private Class<? extends Capabilities> capabilitiesClass;
    private boolean available;

    public ReflectiveCapabilitiesFactory(String name, String capabilitiesClassName, Object... args) {
        this.name = name;
        this.capabilitiesClassName = capabilitiesClassName;
        this.args = args;
        try {
            this.capabilitiesClass = (Class<? extends Capabilities>) Class.forName(capabilitiesClassName);
            this.available = Capabilities.class.isAssignableFrom(this.capabilitiesClass);
        } catch (ClassNotFoundException e) {
            this.available = false;
        }
    }

    public ReflectiveCapabilitiesFactory(String name, Class<? extends Capabilities> capabilitiesClass, Object... args) {
        this.name = name;
        this.capabilitiesClass = capabilitiesClass;
        this.args = args;
        this.capabilitiesClassName = capabilitiesClass.getName();
        this.available = Capabilities.class.isAssignableFrom(this.capabilitiesClass);
    }

    public Class<? extends Capabilities> getCapabilitiesClass() {
        return capabilitiesClass;
    }

    public boolean isAvailable() {
        return available;
    }

    protected DesiredCapabilities newDefaultCapabilities() {
        return null;
    }

    @Override
    public Capabilities newCapabilities() {
        if (!available) {
            throw new ConfigurationException("Capabilities " + capabilitiesClassName + " is not available.");
        }

        try {
            return ReflectionUtils.newInstance(capabilitiesClass, args);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new ConfigurationException("Can't create new Capabilities instance", e);
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getAlternativeNames() {
        if (capabilitiesClass != null) {
            return new String[]{capabilitiesClass.getName(), capabilitiesClass.getSimpleName()};
        }
        return new String[0];
    }

    @Override
    public int getPriority() {
        return 0;
    }
}
