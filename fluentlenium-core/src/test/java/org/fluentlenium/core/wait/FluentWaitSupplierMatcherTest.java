package org.fluentlenium.core.wait;

import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.FluentConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitSupplierMatcherTest {
    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

    @Mock
    private FluentWebElement fluentWebElement;

    @Mock
    private WebElement element;

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement.conditions()).thenReturn(new WebElementConditions(fluentWebElement));
        when(fluentWebElement.getElement()).thenReturn(element);
        when(fluentWebElement.now()).thenReturn(fluentWebElement);
    }

    @After
    public void after() {
        reset(fluent);
        reset(fluentWebElement);
        reset(element);
    }

    @Test
    public void isEnabled() {
        FluentConditions matcher = wait.untilElement(() -> fluentWebElement);
        assertThatThrownBy(() -> matcher.enabled()).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.enabled();

        verify(fluentWebElement, atLeastOnce()).enabled();

        assertThatThrownBy(() -> matcher.not().enabled()).isExactlyInstanceOf(TimeoutException.class);
    }
}
