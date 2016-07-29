package org.fluentlenium.core.wait;

import com.google.common.base.Suppliers;
import org.assertj.core.api.ThrowableAssert;
import org.fluentlenium.adapter.FluentAdapter;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.context.FluentThread;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentListImpl;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.search.Search;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FluentWaitSupplierListMatcherTest {
    @Mock
    private Search search;

    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentAdapter fluentAdapter;

    @Mock
    private FluentWebElement fluentWebElement1;

    @Mock
    private FluentWebElement fluentWebElement2;

    @Mock
    private FluentWebElement fluentWebElement3;

    private List<FluentWebElement> fluentWebElements;

    @Mock
    private WebElement element1;

    @Mock
    private WebElement element2;

    @Mock
    private WebElement element3;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
        FluentThread.set(fluentAdapter);

        wait = new FluentWait(fluent, search);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement1.conditions()).thenReturn(new WebElementConditions(fluentWebElement1));
        when(fluentWebElement1.getElement()).thenReturn(element1);

        when(fluentWebElement2.conditions()).thenReturn(new WebElementConditions(fluentWebElement2));
        when(fluentWebElement2.getElement()).thenReturn(element2);

        when(fluentWebElement3.conditions()).thenReturn(new WebElementConditions(fluentWebElement3));
        when(fluentWebElement3.getElement()).thenReturn(element3);

        fluentWebElements = Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3);
    }

    @After
    public void after() {
        reset(search);
        reset(fluent);
        reset(fluentWebElement1);
        reset(fluentWebElement2);
        reset(fluentWebElement3);
        reset(element1);
        reset(element2);
        reset(element3);
    }

    @Test
    public void isEnabled() {
        final FluentWaitSupplierListMatcher matcher = new FluentWaitSupplierListMatcher(search, wait, Suppliers.ofInstance(new FluentListImpl<FluentWebElement>(fluentWebElements)));
        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, atLeastOnce()).isEnabled();
        verify(fluentWebElement3, atLeastOnce()).isEnabled();

        when(fluentWebElement1.isEnabled()).thenReturn(true);
        when(fluentWebElement2.isEnabled()).thenReturn(true);
        when(fluentWebElement3.isEnabled()).thenReturn(true);
        matcher.isEnabled();

        verify(fluentWebElement1, atLeastOnce()).isEnabled();
        verify(fluentWebElement2, atLeastOnce()).isEnabled();
        verify(fluentWebElement3, atLeastOnce()).isEnabled();

        assertThatThrownBy(new ThrowableAssert.ThrowingCallable() {
            @Override
            public void call() throws Throwable {
                matcher.not().isEnabled();
            }
        }).isExactlyInstanceOf(TimeoutException.class);
    }

}
