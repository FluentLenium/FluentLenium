package org.fluentlenium.adapter;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.AssumptionViolatedException;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FluentTestRuleTest {
    @Mock
    private Statement base;

    @Mock
    private Description description;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void after() {
        reset(base, description);
    }

    private static class TestException extends Exception {

    }

    @Test
    public void when_no_error_everything_is_called() throws Throwable {
        final FluentTestRule testRule = spy(new FluentTestRule());

        testRule.apply(base, description).evaluate();

        verify(testRule).starting(description);
        verify(testRule).succeeded(description);
        verify(base).evaluate();
        verify(testRule, never()).failed(any(Throwable.class), eq(description));
        verify(testRule).finished(description);
    }

    @Test
    public void when_init_fails_test_is_not_called() throws Throwable {
        final FluentTestRule testRule = spy(new FluentTestRule());

        doThrow(TestException.class).when(testRule).starting(description);

        Assertions.assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                testRule.apply(base, description).evaluate();
            }
        }).isExactlyInstanceOf(TestException.class);

        verify(base, never()).evaluate();
        verify(testRule, never()).succeeded(description);
        verify(testRule).failed(any(TestException.class), eq(description));
        verify(testRule).finished(description);
    }
}
