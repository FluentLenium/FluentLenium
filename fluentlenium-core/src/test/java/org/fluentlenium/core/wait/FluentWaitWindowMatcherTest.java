package org.fluentlenium.core.wait;


import com.google.common.base.Predicate;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.context.FluentThread;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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

public class FluentWaitWindowMatcherTest {
    @Mock
    private Search search;

    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentAdapter fluentAdapter;

    @Mock
    private WebDriver webDriver;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        FluentThread.set(fluentAdapter);

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

        final Predicate<FluentWebElement> predicate = new Predicate<FluentWebElement>() {
            @Override
            public boolean apply(FluentWebElement input) {
                return input.isEnabled();
            }
        };

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

        when(webDriver.getWindowHandles()).thenReturn(new HashSet<String>(Arrays.asList("testWindow", "otherWindow")));
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
