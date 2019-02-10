package org.fluentlenium.adapter.cucumber;

import cucumber.api.junit.Cucumber;
import cucumber.runtime.java.fluentlenium.FluentObjectFactory;
import org.fluentlenium.configuration.FluentConfiguration;
import org.junit.runners.model.InitializationError;

import static java.util.Objects.nonNull;

/**
 * Pass this class to JUnit @RunWith() annotation to enable FluentLenium dependency injection for Cucumber steps.
 * To use annotation configuration annotate class with @RunWith(FluentCucumber.class) and not on BaseTest or any other
 * class containing Cucumber steps.
 *
 * If you want to use extending your test classes with
 */
public class FluentCucumber extends Cucumber {

    public FluentCucumber(Class clazz) throws InitializationError {
        this(clazz, shouldInitConfiguration(clazz));
    }

    public FluentCucumber(Class clazz, Object obj) throws InitializationError { // NOPMD
        super(clazz);
    }

    private static boolean shouldInitConfiguration(Class clazz) {
        if (nonNull(clazz.getAnnotation(FluentConfiguration.class))) {
            FluentObjectFactory.setInitClass(clazz);
        } else {
            FluentObjectFactory.setInitClass(null);
        }
        return true;
    }
}
