package org.fluentlenium.core.wait;

import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentDriver;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;

import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitWindowMatcherTest {
    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private WebDriver webDriver;

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);
    }

    @After
    public void after() {
        reset(fluent);
    }

    @Test
    public void testWindow() {
        when(fluent.getDriver()).thenReturn(webDriver);

        final FluentWaitWindowConditions matcher = new FluentWaitWindowConditions(wait, "testWindow");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.displayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(webDriver, atLeastOnce()).getWindowHandles();
        reset(webDriver);

        matcher.notDisplayed();

        when(webDriver.getWindowHandles()).thenReturn(new HashSet<>(Arrays.asList("testWindow", "otherWindow")));
        matcher.displayed();

        verify(webDriver, atLeastOnce()).getWindowHandles();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.notDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(webDriver, atLeastOnce()).getWindowHandles();
    }
}
