package org.fluentlenium.core.wait;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.conditions.FluentListConditions;
import org.fluentlenium.core.conditions.WebElementConditions;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

@RunWith(MockitoJUnitRunner.class)
@SuppressWarnings("PMD.ExcessivePublicCount")
public class FluentWaitElementListMatcherTest {
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

    @Before
    public void before() {
        wait = new FluentWait(fluent);
        wait.atMost(1L, TimeUnit.MILLISECONDS);
        wait.pollingEvery(1L, TimeUnit.MILLISECONDS);

        when(fluentWebElement1.conditions()).thenReturn(new WebElementConditions(fluentWebElement1));
        when(fluentWebElement1.getElement()).thenReturn(element1);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);

        when(fluentWebElement2.conditions()).thenReturn(new WebElementConditions(fluentWebElement2));
        when(fluentWebElement2.getElement()).thenReturn(element2);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);

        when(fluentWebElement3.conditions()).thenReturn(new WebElementConditions(fluentWebElement3));
        when(fluentWebElement3.getElement()).thenReturn(element3);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);

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
    public void isVerified() {
        Predicate<FluentWebElement> predicate = FluentWebElement::enabled;

        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.verify(predicate)).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();

        reset(fluentWebElement1, fluentWebElement2, fluentWebElement3);

        when(fluentWebElement1.enabled()).thenReturn(true);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);
        when(fluentWebElement2.enabled()).thenReturn(true);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);
        when(fluentWebElement3.enabled()).thenReturn(true);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);

        matcher.verify(predicate);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, never()).enabled();
        verify(fluentWebElement3, never()).enabled();
    }

    @Test
    public void isVerifiedEmpty() {
        Predicate<FluentWebElement> predicate = FluentWebElement::enabled;

        FluentListConditions matcher = wait.until(new ArrayList<>());
        assertThatThrownBy(() -> matcher.verify(predicate, false)).isExactlyInstanceOf(TimeoutException.class);

        matcher.verify(predicate, true);

        assertThatThrownBy(() -> matcher.not().verify(predicate, true)).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isNotVerified() {
        Predicate<FluentWebElement> predicate = input -> !input.enabled();

        FluentListConditions matcher = wait.until(fluentWebElements);

        assertThatThrownBy(() -> matcher.not().verify(predicate)).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, atLeastOnce()).enabled();
        verify(fluentWebElement3, atLeastOnce()).enabled();

        reset(fluentWebElement1, fluentWebElement2, fluentWebElement3);

        when(fluentWebElement1.enabled()).thenReturn(true);
        when(fluentWebElement1.now()).thenReturn(fluentWebElement1);
        when(fluentWebElement2.enabled()).thenReturn(true);
        when(fluentWebElement2.now()).thenReturn(fluentWebElement2);
        when(fluentWebElement3.enabled()).thenReturn(true);
        when(fluentWebElement3.now()).thenReturn(fluentWebElement3);

        matcher.not().verify(predicate);

        verify(fluentWebElement1, atLeastOnce()).enabled();
        verify(fluentWebElement2, never()).enabled();
        verify(fluentWebElement3, never()).enabled();
    }

    @Test
    public void hasAttribute() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.attribute("test", "value")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).attribute("test");
        verify(fluentWebElement2, atLeastOnce()).attribute("test");
        verify(fluentWebElement3, atLeastOnce()).attribute("test");

        when(fluentWebElement1.attribute("test")).thenReturn("value");
        when(fluentWebElement2.attribute("test")).thenReturn("value");
        when(fluentWebElement3.attribute("test")).thenReturn("value");
        matcher.attribute("test", "value");

        verify(fluentWebElement1, atLeastOnce()).attribute("test");
        verify(fluentWebElement2, atLeastOnce()).attribute("test");
        verify(fluentWebElement3, atLeastOnce()).attribute("test");

        assertThatThrownBy(() -> matcher.not().attribute("test", "value")).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasId() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.id("value")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).id();
        verify(fluentWebElement2, atLeastOnce()).id();
        verify(fluentWebElement3, atLeastOnce()).id();

        when(fluentWebElement1.id()).thenReturn("value");
        when(fluentWebElement2.id()).thenReturn("value");
        when(fluentWebElement3.id()).thenReturn("value");
        matcher.id("value");

        verify(fluentWebElement1, atLeastOnce()).id();
        verify(fluentWebElement2, atLeastOnce()).id();
        verify(fluentWebElement3, atLeastOnce()).id();

        assertThatThrownBy(() -> matcher.not().id("value")).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasName() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.name("name")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).name();
        verify(fluentWebElement2, atLeastOnce()).name();
        verify(fluentWebElement3, atLeastOnce()).name();

        when(fluentWebElement1.name()).thenReturn("name");
        when(fluentWebElement2.name()).thenReturn("name");
        when(fluentWebElement3.name()).thenReturn("name");
        matcher.name("name");

        verify(fluentWebElement1, atLeastOnce()).name();
        verify(fluentWebElement2, atLeastOnce()).name();
        verify(fluentWebElement3, atLeastOnce()).name();

        assertThatThrownBy(() -> matcher.not().name("name")).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasText() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.text().equalTo("text")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        when(fluentWebElement1.text()).thenReturn("text");
        when(fluentWebElement2.text()).thenReturn("text");
        when(fluentWebElement3.text()).thenReturn("text");
        matcher.text().equalTo("text");

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        assertThatThrownBy(() -> matcher.not().text().equalTo("text")).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void containsText() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.text().contains("ex")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        when(fluentWebElement1.text()).thenReturn("text");
        when(fluentWebElement2.text()).thenReturn("text");
        when(fluentWebElement3.text()).thenReturn("text");
        matcher.text().contains("ex");

        verify(fluentWebElement1, atLeastOnce()).text();
        verify(fluentWebElement2, atLeastOnce()).text();
        verify(fluentWebElement3, atLeastOnce()).text();

        assertThatThrownBy(() -> matcher.not().text().contains("ex")).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isPresent() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(matcher::present).isExactlyInstanceOf(TimeoutException.class);

        when(fluentWebElement1.present()).thenReturn(true);
        when(fluentWebElement2.present()).thenReturn(true);
        when(fluentWebElement3.present()).thenReturn(true);
        matcher.present();

        verify(fluentWebElement1, atLeastOnce()).present();
        verify(fluentWebElement2, atLeastOnce()).present();
        verify(fluentWebElement3, atLeastOnce()).present();

        assertThatThrownBy(() -> matcher.not().present()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isEnabled() {
        FluentListConditions matcher = wait.until(fluentWebElements);
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

    @Test
    public void proxyIsEnabled() {
        when(fluentWebElement1.now()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement2.now()).thenThrow(NoSuchElementException.class);
        when(fluentWebElement3.now()).thenThrow(NoSuchElementException.class);

        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(matcher::enabled).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(matcher::selected).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).selected();
        verify(fluentWebElement2, atLeastOnce()).selected();
        verify(fluentWebElement3, atLeastOnce()).selected();

        when(fluentWebElement1.selected()).thenReturn(true);
        when(fluentWebElement2.selected()).thenReturn(true);
        when(fluentWebElement3.selected()).thenReturn(true);
        matcher.selected();

        verify(fluentWebElement1, atLeastOnce()).selected();
        verify(fluentWebElement2, atLeastOnce()).selected();
        verify(fluentWebElement3, atLeastOnce()).selected();

        assertThatThrownBy(() -> matcher.not().selected()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isDisplayed() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(matcher::displayed).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).displayed();
        verify(fluentWebElement2, atLeastOnce()).displayed();
        verify(fluentWebElement3, atLeastOnce()).displayed();

        when(fluentWebElement1.displayed()).thenReturn(true);
        when(fluentWebElement2.displayed()).thenReturn(true);
        when(fluentWebElement3.displayed()).thenReturn(true);
        matcher.displayed();

        verify(fluentWebElement1, atLeastOnce()).displayed();
        verify(fluentWebElement2, atLeastOnce()).displayed();
        verify(fluentWebElement3, atLeastOnce()).displayed();

        assertThatThrownBy(() -> matcher.not().displayed()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isClickable() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(matcher::clickable).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).clickable();
        verify(fluentWebElement2, atLeastOnce()).clickable();
        verify(fluentWebElement3, atLeastOnce()).clickable();

        when(fluentWebElement1.clickable()).thenReturn(true);
        when(fluentWebElement2.clickable()).thenReturn(true);
        when(fluentWebElement3.clickable()).thenReturn(true);
        matcher.clickable();

        verify(fluentWebElement1, atLeastOnce()).clickable();
        verify(fluentWebElement2, atLeastOnce()).clickable();
        verify(fluentWebElement3, atLeastOnce()).clickable();

        assertThatThrownBy(() -> matcher.not().clickable()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isStale() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(matcher::stale).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement1, atLeastOnce()).stale();
        verify(fluentWebElement2, atLeastOnce()).stale();
        verify(fluentWebElement3, atLeastOnce()).stale();

        when(fluentWebElement1.stale()).thenReturn(true);
        when(fluentWebElement2.stale()).thenReturn(true);
        when(fluentWebElement3.stale()).thenReturn(true);
        matcher.stale();

        verify(fluentWebElement1, atLeastOnce()).stale();
        verify(fluentWebElement2, atLeastOnce()).stale();
        verify(fluentWebElement3, atLeastOnce()).stale();

        assertThatThrownBy(() -> matcher.not().stale()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasSize() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.size(2)).isExactlyInstanceOf(TimeoutException.class);

        matcher.size(3);

        assertThatThrownBy(() -> matcher.not().size(3)).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasSizeBuilder() {
        FluentListConditions matcher = wait.until(fluentWebElements);
        assertThatThrownBy(() -> matcher.size().equalTo(2)).isExactlyInstanceOf(TimeoutException.class);

        matcher.size().equalTo(3);

        assertThatThrownBy(() -> matcher.not().size().equalTo(3)).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasRectangle() {
        FluentListConditions matcher = wait.until(fluentWebElements);

        when(element1.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element2.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));
        when(element3.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(() -> matcher.rectangle().x(5)).isExactlyInstanceOf(TimeoutException.class);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, atLeastOnce()).getRect();
        verify(element3, atLeastOnce()).getRect();

        matcher.rectangle().x(1);

        verify(element1, atLeastOnce()).getRect();
        verify(element2, atLeastOnce()).getRect();
        verify(element3, atLeastOnce()).getRect();

        assertThatThrownBy(() -> matcher.not().rectangle().x(1)).isExactlyInstanceOf(TimeoutException.class);
    }

}
