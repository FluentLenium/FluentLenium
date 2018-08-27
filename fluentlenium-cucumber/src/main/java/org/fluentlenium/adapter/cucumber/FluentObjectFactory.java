package org.fluentlenium.adapter.cucumber;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;
import org.fluentlenium.configuration.FluentConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container
 */
public class FluentObjectFactory implements ObjectFactory {

    private FluentCucumberTestContainer testContainer;

    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Set<Class<?>> classes = new HashSet<>();
    private Class<?> configClass;

    /**
     * Creating instance of FluentObjectFactory and sets FluentCucumberTest instance.
     *
     * @param testContainer testContainer
     */
    public FluentObjectFactory(FluentCucumberTestContainer testContainer) {
        this.testContainer = testContainer;
    }

    @Override
    public void start() {
        testContainer.instance().before();
    }

    @Override
    public void stop() {
        testContainer.instance().after();
        this.instances.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        classes.add(aClass);
        if(isNull(configClass)) {
            configClass = checkClassForConfiguration(aClass);
            if (nonNull(configClass)) {
                testContainer.setConfigClass(configClass);
            }
        }
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        try {
            T instance = type.cast(instances.get(type));
            if (instance == null) {
                instance = cacheNewInstance(type);
            }
            return (T) testContainer.instance().inject(instance).getContainer();
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    private <T> T cacheNewInstance(Class<T> type) {
        try {
            T instance = testContainer.instance().newInstance(type);
            instances.put(type, instance);
            return instance;
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    private Class<?> checkClassForConfiguration(Class<?> cls) {
        Class superClass = cls.getSuperclass();
        if (superClass != null && superClass.isAnnotationPresent(FluentConfiguration.class)) {
            return superClass;
        } else if (cls.isAnnotationPresent(FluentConfiguration.class)) {
            return cls;
        } else {
            return null;
        }
    }
}
