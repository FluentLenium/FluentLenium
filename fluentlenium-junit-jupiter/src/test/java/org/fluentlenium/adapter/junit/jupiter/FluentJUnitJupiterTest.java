package org.fluentlenium.adapter.junit.jupiter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class FluentJUnitJupiterTest {
    @Mock
    private ExtensionContext context;
    private FluentJUnitJupiter sut;

    static class Test1 extends FluentTest {
        void test1() {

        }
    }

    private Test1 test;

    @BeforeEach
    void beforeEach() throws Exception {
        MockitoAnnotations.openMocks(this);
        WebDriverManager.chromedriver().setup();

        sut = new FluentJUnitJupiter();
        test = spy(new Test1());

        when(context.getTestInstance()).thenReturn(Optional.of(test));
        when(context.getTestClass()).thenReturn(Optional.of(Test1.class));
        when(context.getTestMethod()).thenReturn(Optional.of(test.getClass().getDeclaredMethod("test1")));
    }

    @Test
    void testBeforeEach() {
        sut.beforeEach(context);
        verify(test)._starting(eq(Test1.class), eq("test1"));
    }

    @Test
    void testAfterEachSuccessful() {
        when(context.getExecutionException()).thenReturn(Optional.empty());

        sut.afterEach(context);

        verify(test)._finished(eq(Test1.class), eq("test1"));
        verify(test, never())._failed(any(), any(), any());
    }

    @Test
    void testAfterEachFailure() {
        final AssertionError error = new AssertionError("error");
        when(context.getExecutionException()).thenReturn(Optional.of(error));

        sut.afterEach(context);

        verify(test)._failed(same(error), eq(Test1.class), eq("test1"));
        verify(test, never())._finished(any(), any());
    }

    @Test
    void badInstance() {
        when(context.getTestInstance()).thenReturn(Optional.of(this)); // not FluentTest

        assertThrows(IllegalStateException.class, () -> sut.beforeEach(context));
        assertThrows(IllegalStateException.class, () -> sut.afterEach(context));
    }
}
