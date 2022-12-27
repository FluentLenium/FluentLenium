package io.fluentlenium.adapter.junit;

import org.assertj.core.api.Assertions;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.model.Statement;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
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

    //CHECKSTYLE.OFF: IllegalThrows
    @Test
    public void whenNoErrorEverythingIsCalled() throws Throwable {
        FluentTestRule testRule = spy(new FluentTestRule(this));

        testRule.apply(base, description).evaluate();

        verify(testRule).starting(description);
        verify(testRule).succeeded(description);
        verify(base).evaluate();
        verify(testRule, never()).failed(any(Throwable.class), eq(description));
        verify(testRule).finished(description);
    }

    @Test
    public void whenInitFailsTestIsNotCalled() throws Throwable {
        FluentTestRule testRule = spy(new FluentTestRule(this));

        doThrow(RuntimeException.class).when(testRule).starting(description);

        Assertions.assertThatThrownBy(() -> testRule.apply(base, description).evaluate())
                .isExactlyInstanceOf(RuntimeException.class);

        verify(base, never()).evaluate();
        verify(testRule, never()).succeeded(description);
        verify(testRule).failed(any(RuntimeException.class), eq(description));
        verify(testRule).finished(description);
    }
    //CHECKSTYLE.ON: IllegalThrows
}
