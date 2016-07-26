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

public class AtLeastOneElementConditionsTest extends AbstractFluentListConditionsTest {
    private AtLeastOneElementConditions conditions;

    @Before
    public void before() {
        super.before();
        conditions = new AtLeastOneElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement2, fluentWebElement3));
    }

    @After
    public void after() {
        reset(webElement1);
        reset(webElement2);
        reset(webElement3);
    }

    @Test
    public void isVerified() {
        assertThat(conditions.isVerified(Predicates.<FluentWebElement>alwaysTrue())).isTrue();
        assertThat(conditions.isVerified(Predicates.<FluentWebElement>alwaysFalse())).isFalse();

        assertThat(conditions.not().isVerified(Predicates.<FluentWebElement>alwaysTrue())).isFalse();
        assertThat(conditions.not().isVerified(Predicates.<FluentWebElement>alwaysFalse())).isTrue();
    }

    @Test
    public void isPresent() {
        assertThat(conditions.isPresent()).isTrue();
        assertThat(conditions.not().isPresent()).isFalse();

        AtLeastOneElementConditions emptyConditions = new AtLeastOneElementConditions(Collections.<FluentWebElement>emptyList());

        assertThat(emptyConditions.isPresent()).isFalse();
        assertThat(emptyConditions.not().isPresent()).isTrue();
    }

    @Test
    public void isClickable() {
        assertThat(conditions.isClickable()).isFalse();

        when(webElement1.isEnabled()).thenReturn(true);
        when(webElement1.isDisplayed()).thenReturn(true);

        assertThat(conditions.isClickable()).isTrue();
    }

    @Test
    public void isStale() {
        assertThat(conditions.isStale()).isFalse();

        // Selenium invokes isEnabled to check staleness.
        when(webElement2.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(conditions.isStale()).isTrue();
    }

    @Test
    public void isEnabled() {
        assertThat(conditions.isEnabled()).isFalse();

        when(webElement3.isEnabled()).thenReturn(true);

        assertThat(conditions.isEnabled()).isTrue();
    }

    @Test
    public void isDisplayed() {
        assertThat(conditions.isDisplayed()).isFalse();

        when(webElement1.isDisplayed()).thenReturn(true);

        assertThat(conditions.isDisplayed()).isTrue();
    }

    @Test
    public void isSelected() {
        assertThat(conditions.isSelected()).isFalse();

        when(webElement2.isSelected()).thenReturn(true);

        assertThat(conditions.isSelected()).isTrue();
    }

    @Test
    public void hasText() {
        when(webElement3.getText()).thenReturn("Some Text");

        assertThat(conditions.hasText("Some Text")).isTrue();
        assertThat(conditions.hasText("Other Text")).isFalse();
    }

    @Test
    public void containsText() {
        when(webElement1.getText()).thenReturn("Some Text");

        assertThat(conditions.containsText("Te")).isTrue();
        assertThat(conditions.containsText("Other")).isFalse();
    }

    @Test
    public void hasAttribute() {
        assertThat(conditions.hasAttribute("attr", "value")).isFalse();

        when(webElement2.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.hasAttribute("attr", "value")).isTrue();
    }

    @Test
    public void hasId() {
        assertThat(conditions.hasId("value")).isFalse();

        when(webElement3.getAttribute("id")).thenReturn("value");

        assertThat(conditions.hasId("value")).isTrue();
    }

    @Test
    public void hasName() {
        assertThat(conditions.hasName("value")).isFalse();

        when(webElement1.getAttribute("name")).thenReturn("value");

        assertThat(conditions.hasName("value")).isTrue();
    }

    @Test
    public void hasSize() {
        assertThat(conditions.hasSize(3)).isTrue();
        assertThat(conditions.hasSize().equalTo(3)).isTrue();
        assertThat(conditions.hasSize(2)).isFalse();
        assertThat(conditions.hasSize().equalTo(2)).isFalse();

        AtLeastOneElementConditions conditions2 = new AtLeastOneElementConditions(Arrays.asList(fluentWebElement1, fluentWebElement3));

        assertThat(conditions2.hasSize(3)).isFalse();
        assertThat(conditions2.hasSize().equalTo(3)).isFalse();
        assertThat(conditions2.hasSize(2)).isTrue();
        assertThat(conditions2.hasSize().equalTo(2)).isTrue();
    }

    @Test
    public void defaultValueWhenEmpty() {
        AtLeastOneElementConditions conditions = new AtLeastOneElementConditions(Arrays.<FluentWebElement>asList());

        assertThat(conditions.isEnabled()).isFalse();
    }
}
