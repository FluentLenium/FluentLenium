package org.fluentlenium.core.wait;


import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.search.Search;
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
    private Search search;

    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private WebDriver webDriver;

    @Before
    public void before() {
        wait = new FluentWait(fluent, search);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);
    }

    @After
    public void after() {
        reset(search);
        reset(fluent);
    }

    @Test
    public void testWindow() {
        when(fluent.getDriver()).thenReturn(webDriver);

        final FluentWaitWindowMatcher matcher = new FluentWaitWindowMatcher(wait, "testWindow");
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(webDriver, atLeastOnce()).getWindowHandles();
        reset(webDriver);

        matcher.isNotDisplayed();

        when(webDriver.getWindowHandles()).thenReturn(new HashSet<>(Arrays.asList("testWindow", "otherWindow")));
        matcher.isDisplayed();

        verify(webDriver, atLeastOnce()).getWindowHandles();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isNotDisplayed();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(webDriver, atLeastOnce()).getWindowHandles();
    }
}
