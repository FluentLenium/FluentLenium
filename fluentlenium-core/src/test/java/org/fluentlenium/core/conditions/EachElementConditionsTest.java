package org.fluentlenium.core.conditions;

import com.google.common.base.Predicates;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

public class EachElementConditionsTest extends AbstractFluentListConditionsTest {
    private EachElementConditions conditions;

    @Before
    @Override
    public void before() {
        super.before();
        conditions = new EachElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));
    }

    @After
    public void after() {
        reset(webElement1);
        reset(webElement2);
        reset(webElement3);
    }

    @Test
    public void isVerified() {
        assertThat(conditions.verify(Predicates.<FluentWebElement>alwaysTrue())).isTrue();
        assertThat(conditions.verify(Predicates.<FluentWebElement>alwaysFalse())).isFalse();

        assertThat(conditions.not().verify(Predicates.<FluentWebElement>alwaysTrue())).isFalse();
        assertThat(conditions.not().verify(Predicates.<FluentWebElement>alwaysFalse())).isTrue();
    }

    @Test
    public void isPresent() {
        assertThat(conditions.present()).isTrue();
        assertThat(conditions.not().present()).isFalse();


        EachElementConditions emptyConditions = new EachElementConditions(Collections.<FluentWebElement>emptyList());

        assertThat(emptyConditions.present()).isFalse();
        assertThat(emptyConditions.not().present()).isTrue();
    }

    @Test
    public void isClickable() {
        when(webElement1.isEnabled()).thenReturn(true);
        when(webElement1.isDisplayed()).thenReturn(true);

        assertThat(conditions.clickable()).isFalse();

        when(webElement2.isEnabled()).thenReturn(true);
        when(webElement2.isDisplayed()).thenReturn(true);
        when(webElement3.isEnabled()).thenReturn(true);
        when(webElement3.isDisplayed()).thenReturn(true);

        assertThat(conditions.clickable()).isTrue();
    }

    @Test
    public void isStale() {
        when(webElement1.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(conditions.stale()).isFalse();

        // Selenium invokes isEnabled to check staleness.
        when(webElement2.isEnabled()).thenThrow(StaleElementReferenceException.class);
        when(webElement3.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(conditions.stale()).isTrue();
    }

    @Test
    public void isEnabled() {
        when(webElement1.isEnabled()).thenReturn(true);

        assertThat(conditions.enabled()).isFalse();

        when(webElement2.isEnabled()).thenReturn(true);
        when(webElement3.isEnabled()).thenReturn(true);

        assertThat(conditions.enabled()).isTrue();
    }

    @Test
    public void isDisplayed() {
        when(webElement1.isDisplayed()).thenReturn(true);

        assertThat(conditions.displayed()).isFalse();

        when(webElement2.isDisplayed()).thenReturn(true);
        when(webElement3.isDisplayed()).thenReturn(true);

        assertThat(conditions.displayed()).isTrue();
    }

    @Test
    public void isSelected() {
        when(webElement1.isSelected()).thenReturn(true);

        assertThat(conditions.selected()).isFalse();

        when(webElement2.isSelected()).thenReturn(true);
        when(webElement3.isSelected()).thenReturn(true);

        assertThat(conditions.selected()).isTrue();
    }

    @Test
    public void hasText() {
        when(webElement1.getText()).thenReturn("Some Text");
        when(webElement2.getText()).thenReturn("Some Text");
        when(webElement3.getText()).thenReturn("Some Text");

        assertThat(conditions.text().equalsTo("Some Text")).isTrue();

        reset(webElement3);
        assertThat(conditions.text().equalsTo("Other Text")).isFalse();
    }

    @Test
    public void containsText() {
        when(webElement1.getText()).thenReturn("Some Text");
        when(webElement2.getText()).thenReturn("Some Text");
        when(webElement3.getText()).thenReturn("Some Text");

        assertThat(conditions.text().contains("Te")).isTrue();

        reset(webElement3);
        assertThat(conditions.text().contains("Other")).isFalse();
    }

    @Test
    public void hasAttribute() {
        when(webElement1.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.attribute("attr", "value")).isFalse();

        when(webElement2.getAttribute("attr")).thenReturn("value");
        when(webElement3.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.attribute("attr", "value")).isTrue();
    }

    @Test
    public void hasId() {
        when(webElement1.getAttribute("id")).thenReturn("value");

        assertThat(conditions.id("value")).isFalse();

        when(webElement2.getAttribute("id")).thenReturn("value");
        when(webElement3.getAttribute("id")).thenReturn("value");

        assertThat(conditions.id("value")).isTrue();
    }

    @Test
    public void hasName() {
        when(webElement1.getAttribute("name")).thenReturn("value");

        assertThat(conditions.name("value")).isFalse();

        when(webElement2.getAttribute("name")).thenReturn("value");
        when(webElement3.getAttribute("name")).thenReturn("value");

        assertThat(conditions.name("value")).isTrue();
    }

    @Test
    public void hasSize() {
        assertThat(conditions.hasSize(3)).isTrue();
        assertThat(conditions.hasSize().equalTo(3)).isTrue();
        assertThat(conditions.hasSize(2)).isFalse();
        assertThat(conditions.hasSize().equalTo(2)).isFalse();

        assertThat(conditions.not().hasSize(3)).isFalse();
        assertThat(conditions.not().hasSize().equalTo(3)).isFalse();
        assertThat(conditions.not().hasSize(2)).isTrue();
        assertThat(conditions.not().hasSize().equalTo(2)).isTrue();

        EachElementConditions conditions2 = new EachElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement3));

        assertThat(conditions2.hasSize(3)).isFalse();
        assertThat(conditions2.hasSize().equalTo(3)).isFalse();
        assertThat(conditions2.hasSize(2)).isTrue();
        assertThat(conditions2.hasSize().equalTo(2)).isTrue();
    }

    @Test
    public void defaultValueWhenEmpty() {
        EachElementConditions conditions = new EachElementConditions(Arrays.<FluentWebElement>asList());

        assertThat(conditions.enabled()).isFalse();
    }
}
