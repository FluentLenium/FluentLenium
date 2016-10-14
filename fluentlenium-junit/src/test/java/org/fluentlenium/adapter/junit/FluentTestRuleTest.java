package org.fluentlenium.adapter.junit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

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

    //CHECKSTYLE.OFF: IllegalThrows
    @Test
    public void whenNoErrorEverythingIsCalled() throws Throwable {
        final FluentTestRule testRule = spy(new FluentTestRule(this));

        testRule.apply(base, description).evaluate();

        verify(testRule).starting(description);
        verify(testRule).succeeded(description);
        verify(base).evaluate();
        verify(testRule, never()).failed(any(Throwable.class), eq(description));
        verify(testRule).finished(description);
    }

    @Test
    public void whenInitFailsTestIsNotCalled() throws Throwable {
        final FluentTestRule testRule = spy(new FluentTestRule(this));

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
    //CHECKSTYLE.ON: IllegalThrows
}
