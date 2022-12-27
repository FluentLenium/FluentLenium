package io.fluentlenium.core.conditions;

import io.fluentlenium.adapter.FluentAdapter;import io.fluentlenium.core.components.DefaultComponentInstantiator;import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.adapter.FluentAdapter;
import io.fluentlenium.core.components.DefaultComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WebElementConditionsTest {

    @Mock
    private WebElement webElement;

    @Mock
    private WebDriver webDriver;

    private FluentWebElement fluentWebElement;
    private WebElementConditions conditions;

    private FluentAdapter fluentAdapter;

    @Before
    public void before() {
        fluentAdapter = new FluentAdapter();
        fluentAdapter.initFluent(webDriver);

        fluentWebElement = new FluentWebElement(webElement, fluentAdapter, new DefaultComponentInstantiator(fluentAdapter));
        conditions = new WebElementConditions(fluentWebElement);
    }

    @After
    public void after() {
        reset(webElement);
    }

    @Test
    public void isVerified() {
        assertThat(conditions.verify(predicate -> true)).isTrue();
        assertThat(conditions.verify(predicate -> false)).isFalse();

        assertThat(conditions.not().verify(predicate -> true)).isFalse();
        assertThat(conditions.not().verify(predicate -> false)).isTrue();
    }

    @Test
    public void isClickable() {
        assertThat(conditions.clickable()).isFalse();

        when(webElement.isEnabled()).thenReturn(true);
        when(webElement.isDisplayed()).thenReturn(true);

        assertThat(conditions.clickable()).isTrue();
    }

    @Test
    public void isStale() {
        assertThat(conditions.stale()).isFalse();

        // Selenium invokes enabled to check staleness.
        when(webElement.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(conditions.stale()).isTrue();
    }

    @Test
    public void isEnabled() {
        assertThat(conditions.enabled()).isFalse();

        when(webElement.isEnabled()).thenReturn(true);

        assertThat(conditions.enabled()).isTrue();
    }

    @Test
    public void isDisplayed() {
        assertThat(conditions.displayed()).isFalse();

        when(webElement.isDisplayed()).thenReturn(true);

        assertThat(conditions.displayed()).isTrue();
    }

    @Test
    public void isSelected() {
        assertThat(conditions.selected()).isFalse();

        when(webElement.isSelected()).thenReturn(true);

        assertThat(conditions.selected()).isTrue();
    }

    @Test
    public void hasText() {
        when(webElement.getText()).thenReturn("Some Text");

        assertThat(conditions.text().equalTo("Some Text")).isTrue();
        assertThat(conditions.text().equalTo("Other Text")).isFalse();
    }

    @Test
    public void containsText() {
        when(webElement.getText()).thenReturn("Some Text");

        assertThat(conditions.text().contains("Te")).isTrue();
        assertThat(conditions.text().contains("Other")).isFalse();
    }

    @Test
    public void hasAttribute() {
        assertThat(conditions.attribute("attr", "value")).isFalse();

        when(webElement.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.attribute("attr", "value")).isTrue();
    }

    @Test
    public void hasId() {
        assertThat(conditions.id("value")).isFalse();

        when(webElement.getAttribute("id")).thenReturn("value");

        assertThat(conditions.id("value")).isTrue();
    }

    @Test
    public void hasName() {
        assertThat(conditions.name("value")).isFalse();

        when(webElement.getAttribute("name")).thenReturn("value");

        assertThat(conditions.name("value")).isTrue();
    }

    @Test
    public void className() {
        assertThat(conditions.className("some-class-2")).isFalse();

        when(webElement.getAttribute("class")).thenReturn("some-class-1 some-class-2 some-class-3");

        assertThat(conditions.className("some-class-1")).isTrue();
        assertThat(conditions.className("some-class-2")).isTrue();
        assertThat(conditions.className("some-class-3")).isTrue();
        assertThat(conditions.className("some-class-4")).isFalse();
    }

}
