package org.fluentlenium.core.wait;

import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.components.DefaultComponentInstantiator;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
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

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitSupplierListMatcherTest {
    @Mock
    private FluentDriver fluent;

    private FluentWait wait;

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

    private DefaultComponentInstantiator instantiator;

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(10L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        instantiator = new DefaultComponentInstantiator(fluent);

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
        FluentListConditions matcher = wait.untilElements(() -> instantiator.newFluentList(fluentWebElements));
        assertThatThrownBy(matcher::enabled).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();

        when(fluentWebElement1.enabled()).thenReturn(true);
        when(fluentWebElement2.enabled()).thenReturn(true);
        when(fluentWebElement3.enabled()).thenReturn(true);
        matcher.enabled();

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();

        assertThatThrownBy(() -> matcher.not().enabled()).isExactlyInstanceOf(TimeoutException.class);
    }

}
