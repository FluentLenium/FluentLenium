package io.fluentlenium.core.wait;

import io.fluentlenium.core.FluentDriver;
import io.fluentlenium.core.conditions.FluentConditions;
import io.fluentlenium.core.conditions.WebElementConditions;
import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class FluentWaitElementMatcherTest {
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
        wait.atMost(10L, TimeUnit.MILLISECONDS);
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
    public void isVerified() {
        Predicate<FluentWebElement> predicate = FluentWebElement::enabled;

        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.verify(predicate)).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.verify(predicate);

        verify(fluentWebElement, atLeastOnce()).enabled();
    }

    @Test
    public void isNotVerified() {
        Predicate<FluentWebElement> predicate = input -> !input.enabled();

        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.not().verify(predicate)).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.not().verify(predicate);

        verify(fluentWebElement, atLeastOnce()).enabled();
    }

    @Test
    public void hasAttribute() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.attribute("test", "value")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).attribute("test");

        when(fluentWebElement.attribute("test")).thenReturn("value");
        matcher.attribute("test", "value");

        verify(fluentWebElement, atLeastOnce()).attribute("test");

        matcher.not().attribute("test", "not");
    }

    @Test
    public void hasId() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.id("value")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).id();

        when(fluentWebElement.id()).thenReturn("value");
        matcher.id("value");

        verify(fluentWebElement, atLeastOnce()).id();

        matcher.not().id("not");
    }

    @Test
    public void hasName() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.name("name")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).name();

        when(fluentWebElement.name()).thenReturn("name");
        matcher.name("name");

        verify(fluentWebElement, atLeastOnce()).name();

        matcher.not().name("not");
    }

    @Test
    public void hasText() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.text().equalTo("text")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).text();

        when(fluentWebElement.text()).thenReturn("text");
        matcher.text().equalTo("text");

        verify(fluentWebElement, atLeastOnce()).text();

        matcher.not().text().equalTo("not");
    }

    @Test
    public void containsText() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(() -> matcher.text().contains("ex")).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).text();

        when(fluentWebElement.text()).thenReturn("text");
        matcher.text().contains("ex");

        verify(fluentWebElement, atLeastOnce()).text();

        matcher.not().text().contains("not");
    }

    @Test
    public void isPresent() {
        when(fluentWebElement.now()).thenThrow(NoSuchElementException.class);

        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::present).isExactlyInstanceOf(TimeoutException.class);

        reset(fluentWebElement);
        when(fluentWebElement.present()).thenReturn(true);
        matcher.present();

        assertThatThrownBy(() -> matcher.not().present()).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isEnabled() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::enabled).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).enabled();

        when(fluentWebElement.enabled()).thenReturn(true);
        matcher.enabled();

        verify(fluentWebElement, atLeastOnce()).enabled();

        assertThatThrownBy(() -> matcher.not().enabled()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void proxyIsEnabled() {
        when(fluentWebElement.tagName()).thenThrow(NoSuchElementException.class);

        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::enabled).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isSelected() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::selected).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).selected();

        when(fluentWebElement.selected()).thenReturn(true);
        matcher.selected();

        verify(fluentWebElement, atLeastOnce()).selected();

        assertThatThrownBy(() -> matcher.not().selected()).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isDisplayed() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::displayed).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).displayed();

        when(fluentWebElement.displayed()).thenReturn(true);
        matcher.displayed();

        verify(fluentWebElement, atLeastOnce()).displayed();

        assertThatThrownBy(() -> matcher.not().displayed()).isExactlyInstanceOf(TimeoutException.class);

    }

    @Test
    public void isClickable() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::clickable).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).clickable();

        when(fluentWebElement.clickable()).thenReturn(true);
        matcher.clickable();

        verify(fluentWebElement, atLeastOnce()).clickable();

        assertThatThrownBy(() -> matcher.not().clickable()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void isStale() {
        FluentConditions matcher = wait.until(fluentWebElement);
        assertThatThrownBy(matcher::stale).isExactlyInstanceOf(TimeoutException.class);

        verify(fluentWebElement, atLeastOnce()).stale();

        when(fluentWebElement.stale()).thenReturn(true);
        matcher.stale();

        verify(fluentWebElement, atLeastOnce()).stale();

        assertThatThrownBy(() -> matcher.not().stale()).isExactlyInstanceOf(TimeoutException.class);
    }

    @Test
    public void hasRectangle() {
        FluentConditions matcher = wait.until(fluentWebElement);

        when(element.getRect()).thenReturn(new Rectangle(1, 2, 100, 200));

        assertThatThrownBy(() -> matcher.rectangle().x(5)).isExactlyInstanceOf(TimeoutException.class);

        verify(element, atLeastOnce()).getRect();

        matcher.rectangle().x(1);

        verify(element, atLeastOnce()).getRect();

        assertThatThrownBy(() -> matcher.not().rectangle().x(1)).isExactlyInstanceOf(TimeoutException.class);
    }
}
