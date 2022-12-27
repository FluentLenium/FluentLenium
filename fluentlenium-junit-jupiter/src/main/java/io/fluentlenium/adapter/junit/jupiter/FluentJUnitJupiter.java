package io.fluentlenium.adapter.junit.jupiter;

import io.fluentlenium.adapter.FluentTestRunnerAdapter;
import io.fluentlenium.adapter.FluentTestRunnerAdapter;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Method;
import java.util.Optional;

/**
 * FluentTest extension for JUnit Jupiter.
 * <p>
 * To use this, annotate FluentTest class with @ExtendWith(FluentJUnitJupiter.class).
 */
public class FluentJUnitJupiter implements BeforeEachCallback, AfterEachCallback, AfterAllCallback {
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
        FluentTestRunnerAdapter.classDriverCleanup(context.getTestClass().orElse(null));
    }

    private FluentTest testInstanceOf(ExtensionContext context) {
        try {
            return (FluentTest) context.getTestInstance().orElse(null);
        } catch (ClassCastException e) {
            throw new IllegalStateException("FluentJUnitJupiter can not be used other than FluentTest class.", e);
        }
    }

    private Class testClassOf(ExtensionContext context) {
        return context.getTestClass().orElse(null);
    }

    private String testNameOf(ExtensionContext context) {
        return context.getTestMethod().map(Method::getName).orElse(null);
    }
}
