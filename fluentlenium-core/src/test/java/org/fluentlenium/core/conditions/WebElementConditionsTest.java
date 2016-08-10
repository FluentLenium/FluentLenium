package org.fluentlenium.core.conditions;

import com.google.common.base.Predicates;
import org.fluentlenium.core.FluentDriver;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class WebElementConditionsTest {

    @Mock
    private WebElement webElement;

    private FluentWebElement fluentWebElement;
    private WebElementConditions conditions;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);

        fluentWebElement = new FluentWebElement(webElement);
        conditions = new WebElementConditions(fluentWebElement);
    }

    @After
    public void after() {
        reset(webElement);
    }

    @Test
    public void isVerified() {
        assertThat(conditions.isVerified(Predicates.<FluentWebElement>alwaysTrue())).isTrue();
        assertThat(conditions.isVerified(Predicates.<FluentWebElement>alwaysFalse())).isFalse();

        assertThat(conditions.not().isVerified(Predicates.<FluentWebElement>alwaysTrue())).isFalse();
        assertThat(conditions.not().isVerified(Predicates.<FluentWebElement>alwaysFalse())).isTrue();
    }

    @Test
    public void isClickable() {
        assertThat(conditions.isClickable()).isFalse();

        when(webElement.isEnabled()).thenReturn(true);
        when(webElement.isDisplayed()).thenReturn(true);

        assertThat(conditions.isClickable()).isTrue();
    }

    @Test
    public void isStale() {
        assertThat(conditions.isStale()).isFalse();

        // Selenium invokes isEnabled to check staleness.
        when(webElement.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(conditions.isStale()).isTrue();
    }

    @Test
    public void isEnabled() {
        assertThat(conditions.isEnabled()).isFalse();

        when(webElement.isEnabled()).thenReturn(true);

        assertThat(conditions.isEnabled()).isTrue();
    }

    @Test
    public void isDisplayed() {
        assertThat(conditions.isDisplayed()).isFalse();

        when(webElement.isDisplayed()).thenReturn(true);

        assertThat(conditions.isDisplayed()).isTrue();
    }

    @Test
    public void isSelected() {
        assertThat(conditions.isSelected()).isFalse();

        when(webElement.isSelected()).thenReturn(true);

        assertThat(conditions.isSelected()).isTrue();
    }

    @Test
    public void hasText() {
        when(webElement.getText()).thenReturn("Some Text");

        assertThat(conditions.hasText("Some Text")).isTrue();
        assertThat(conditions.hasText("Other Text")).isFalse();
    }

    @Test
    public void containsText() {
        when(webElement.getText()).thenReturn("Some Text");

        assertThat(conditions.containsText("Te")).isTrue();
        assertThat(conditions.containsText("Other")).isFalse();
    }

    @Test
    public void hasAttribute() {
        assertThat(conditions.hasAttribute("attr", "value")).isFalse();

        when(webElement.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.hasAttribute("attr", "value")).isTrue();
    }

    @Test
    public void hasId() {
        assertThat(conditions.hasId("value")).isFalse();

        when(webElement.getAttribute("id")).thenReturn("value");

        assertThat(conditions.hasId("value")).isTrue();
    }

    @Test
    public void hasName() {
        assertThat(conditions.hasName("value")).isFalse();

        when(webElement.getAttribute("name")).thenReturn("value");

        assertThat(conditions.hasName("value")).isTrue();
    }

}
