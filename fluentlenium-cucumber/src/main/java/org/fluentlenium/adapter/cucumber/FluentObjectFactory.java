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

    private final FluentCucumberTest fluentTest;

    private final Map<Class<?>, Object> instances = new HashMap<>();
    private final Set<Class<?>> classes = new HashSet<>();

    /**
     * Creating instance of FluentObjectFactory and sets FluentCucumberTest instance.
     */
    FluentObjectFactory() {
        this.fluentTest = FLUENT_TEST.instance();
    }

    @Override
    public void start() {
        fluentTest.before();
        for (Class<?> clazz : classes) {
            cacheNewInstance(clazz);
        }
    }

    @Override
    public void stop() {
        fluentTest.after();
        this.instances.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        classes.add(aClass);
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> type) {
        try {
            return type.cast(instances.get(type));

        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    private <T> void cacheNewInstance(Class<T> type) {
        try {
            T instance = fluentTest.newInstance(type);
            instances.put(type, instance);

        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }
}
