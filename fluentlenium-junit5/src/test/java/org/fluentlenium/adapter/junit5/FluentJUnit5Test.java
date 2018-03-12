package org.fluentlenium.adapter.junit5;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.*;

public class FluentJUnit5Test {
    @Mock
    ExtensionContext context;

    FluentJUnit5 sut;

    static class Test1 extends FluentTest {
        public void test1() {
        }
    }

    private Test1 test;

    @BeforeEach
    void beforeEach() throws Exception {
        MockitoAnnotations.initMocks(this);

        sut = new FluentJUnit5();

        test = spy(new Test1());

        when(context.getTestInstance()).thenReturn(Optional.of(test));
        when(context.getTestClass()).thenReturn(Optional.of(Test1.class));
        when(context.getTestMethod()).thenReturn(Optional.of(test.getClass().getDeclaredMethod("test1")));
    }

    @Test
    void testBeforeEach() throws Exception {
        sut.beforeEach(context);

        verify(test)._starting(eq(Test1.class), eq("test1"));
    }

    @Test
    void testAfterEachSuccessful() throws Exception {
        when(context.getExecutionException()).thenReturn(Optional.ofNullable(null));
        
        sut.afterEach(context);

        verify(test)._finished(eq(Test1.class), eq("test1"));
        verify(test, never())._failed(any(), any(), any());
    }

    @Test
    void testAfterEachFailure() throws Exception {
        final AssertionError error = new AssertionError("error");
        when(context.getExecutionException()).thenReturn(Optional.of(error));

        sut.afterEach(context);

        verify(test)._failed(same(error), eq(Test1.class), eq("test1"));
        verify(test, never())._finished(any(), any());
    }

    @Test
    void badInstance() throws Exception {
        when(context.getTestInstance()).thenReturn(Optional.of(this)); // not FluentTest

        assertThrows(IllegalStateException.class, () -> sut.beforeEach(context));
        assertThrows(IllegalStateException.class, () -> sut.afterEach(context));
    }
}
