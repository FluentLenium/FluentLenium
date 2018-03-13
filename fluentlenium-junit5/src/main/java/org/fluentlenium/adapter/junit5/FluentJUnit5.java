package org.fluentlenium.adapter.junit5;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * FluentTest extension for JUnit 5.
 * <p>
 * To use this, annotate FluentTest class with @ExtendWith(FluentJUnit5.class).
 */
public class FluentJUnit5 implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {
    @Override
    public void beforeEach(ExtensionContext context) {
        FluentTest instance = testInstanceOf(context);
        instance._starting(testClassOf(context), testNameOf(context));
    }

    @Override
    public void afterEach(ExtensionContext context) {
        FluentTest instance = testInstanceOf(context);
        Optional<Throwable> throwable = context.getExecutionException();

        if (throwable.isPresent()) {
            instance._failed(throwable.get(), testClassOf(context), testNameOf(context));
        } else {
            instance._finished(testClassOf(context), testNameOf(context));
        }
    }

    @Override
    public void afterAll(ExtensionContext context) {
        FluentTest.afterClass(context.getTestClass().orElse(null));
    }

    private FluentTest testInstanceOf(ExtensionContext context) {
        try {
            return (FluentTest) context.getTestInstance().orElse(null);
        } catch (ClassCastException e) {
            throw new IllegalStateException("FluentJUnit5 can not be used other than FluentTest class.", e);
        }
    }

    private Class testClassOf(ExtensionContext context) {
        return context.getTestClass().orElse(null);
    }

    private String testNameOf(ExtensionContext context) {
        return context.getTestMethod().map(Method::getName).orElse(null);
    }
}
