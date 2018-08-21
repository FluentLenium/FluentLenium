package org.fluentlenium.adapter.cucumber;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;

import java.util.HashMap;
import java.util.Map;

import static java.util.Objects.isNull;
import static org.fluentlenium.adapter.cucumber.FluentCucumberTestContainer.FLUENT_TEST;

/**
 * It is an object factory for creating Cucumber steps objects in FluentLenium injection container
 */
public class FluentObjectFactory implements ObjectFactory {

    private final FluentCucumberTest fluentTest;

    private final Map<Class<?>, Object> instances = new HashMap<>();

    /**
     * Creating instance of FluentObhectFactory and sets FluentCucumberTest instance.
     */
    FluentObjectFactory() {
        this.fluentTest = FLUENT_TEST.instance();
    }

    @Override
    public void start() {
        fluentTest.before();
    }

    @Override
    public void stop() {
        fluentTest.after();
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
            if (isNull(instance)) {
                instance = fluentTest.newInstance(type);
                cacheNewInstance(type, instance);
            }
            return instance;

        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }

    private <T> void cacheNewInstance(Class<T> type, T instance) {
        try {
            instances.put(type, instance);
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", type), e);
        }
    }
}
