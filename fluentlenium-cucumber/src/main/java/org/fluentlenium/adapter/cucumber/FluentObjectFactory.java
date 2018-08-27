package org.fluentlenium.adapter.cucumber;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container
 */
public class FluentObjectFactory implements ObjectFactory {

    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Set<Class<?>> classes = new HashSet<>();

    /**
     * Creating instance of FluentObjectFactory and sets FluentCucumberTest instance.
     */
    FluentObjectFactory() {
        FLUENT_TEST.instance();
    }

    @Override
    public void start() {
        FLUENT_TEST.instance().before();
    }

    @Override
    public void stop() {
        FLUENT_TEST.instance().after();
        this.instances.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
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

    private <T> T cacheNewInstance(Class<T> type) {
        try {
            T instance = FLUENT_TEST.instance().newInstance(type);
            instances.put(type, instance);
            return instance;
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }
}
