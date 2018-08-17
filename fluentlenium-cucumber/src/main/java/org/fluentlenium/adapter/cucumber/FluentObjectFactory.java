package org.fluentlenium.adapter.cucumber;

import cucumber.api.java.ObjectFactory;
import cucumber.runtime.CucumberException;

import java.util.Collection;
import java.util.HashSet;

public class FluentObjectFactory implements ObjectFactory {

    private FluentCucumberTest fluentTest;

    private final Collection<Class<?>> stepClasses = new HashSet<>();

    public FluentObjectFactory(FluentCucumberTest fluentTest) {
        this.fluentTest = fluentTest;
    }

    @Override
    public void start() {
        fluentTest.before();
    }

    @Override
    public void stop() {
        fluentTest.after();
        this.stepClasses.clear();
    }

    @Override
    public boolean addClass(Class<?> aClass) {
        stepClasses.add(aClass);
        return true;
    }

    @Override
    public <T> T getInstance(Class<T> aClass) {
        try {
            return fluentTest.newInstance(aClass);
        } catch (Exception e) {
            throw new CucumberException(String.format("Failed to instantiate %s", aClass), e);
        }
    }

}
