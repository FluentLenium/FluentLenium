package org.fluentlenium.adapter.cucumber;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;
import org.fluentlenium.configuration.FluentConfiguration;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.fluentlenium.adapter.cucumber.FluentTestContainer.*;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container
 */
public class FluentObjectFactory implements ObjectFactory {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    private Class<?> initClass;
    private Class<?> configClass;

    /**
     * Creating instance of FluentObjectFactory and sets FluentCucumberTest instance.
     *
     * @param initClass class annotated with {@link FluentConfiguration} annotation
     */
    public FluentObjectFactory(Class<?> initClass) {
        this.initClass = initClass;
    }

    @Override
    public void start() {
        if (initClass != null) {
            setConfigClass(initClass);
            FLUENT_TEST.instance();
            FLUENT_TEST.before();
        } else if (configClass != null) {
            setConfigClass(configClass);
            FLUENT_TEST.instance();
        } else {
            setConfigClass(null);
            FLUENT_TEST.instance();
        }
    }

    @Override
    public void stop() {
        if (initClass != null) {
            FLUENT_TEST.after();
        }
        FLUENT_TEST.reset();
        this.instances.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        if (initClass == null && configClass == null) {
            configClass = checkClassForConfiguration(aClass);
            if (nonNull(configClass)) {
                setConfigClass(configClass);
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
            T instance = FLUENT_TEST.injector().newInstance(type);
            FLUENT_TEST.injector().inject(instance);
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
