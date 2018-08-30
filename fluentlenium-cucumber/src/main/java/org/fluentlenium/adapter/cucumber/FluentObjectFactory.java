package org.fluentlenium.adapter.cucumber;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;
import org.fluentlenium.configuration.FluentConfiguration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.*;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.shouldInitialized;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container
 */
public class FluentObjectFactory implements ObjectFactory {

    private final Map<Class<?>, Object> instances = new HashMap<>();
    private Class<?> configClass;

    /**
     * Creating instance of FluentObjectFactory and sets FluentCucumberTest instance.
     */
    public FluentObjectFactory() {
    }

    @Override
    public void start() {
        FLUENT_TEST.instance();
        if (shouldInitialized()) {
            FLUENT_TEST.before();
        }
    }

    @Override
    public void stop() {
        if (shouldInitialized()) {
            FLUENT_TEST.after();
        }
//        FLUENT_TEST.injector().release();
        this.instances.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        if (shouldInitialized()) {
            FLUENT_TEST.instance();
        } else if (configClass == null) {
            configClass = checkClassForConfiguration(aClass);
            if (nonNull(configClass)) {
                setConfigClass(configClass);
                FLUENT_TEST.instance();
            }
        }
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) { // NOPMD
        try {
            T instance = type.cast(instances.get(type));
            if (instance == null) {
                instance = cacheNewInstance(type);
            }
            return instance;
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T cacheNewInstance(Class<T> type) {
        try {
            T instance = FLUENT_TEST.instance().newInstance(type);
            FLUENT_TEST.instance().inject(instance);
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
