package org.fluentlenium.core.conditions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.StaleElementReferenceException;

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
    public void verify() {
        assertThat(conditions.verify(predicate -> true)).isTrue();
        assertThat(conditions.verify(predicate -> false)).isFalse();

        assertThat(conditions.not().verify(predicate -> true)).isFalse();
        assertThat(conditions.not().verify(predicate -> false)).isTrue();
    }

    @Test
    public void present() {
        assertThat(conditions.present()).isTrue();
        assertThat(conditions.not().present()).isFalse();

        AtLeastOneElementConditions emptyConditions = new AtLeastOneElementConditions(Collections.emptyList());

        assertThat(emptyConditions.present()).isFalse();
        assertThat(emptyConditions.not().present()).isTrue();
    }

    @Test
    public void clickable() {
        assertThat(conditions.clickable()).isFalse();

        when(webElement1.isEnabled()).thenReturn(true);
        when(webElement1.isDisplayed()).thenReturn(true);

        assertThat(conditions.clickable()).isTrue();
    }

    @Test
    public void stale() {
        assertThat(conditions.stale()).isFalse();

        // Selenium invokes isEnabled to check staleness.
        when(webElement2.isEnabled()).thenThrow(StaleElementReferenceException.class);

        assertThat(conditions.stale()).isTrue();
    }

    @Test
    public void enabled() {
        assertThat(conditions.enabled()).isFalse();

        when(webElement3.isEnabled()).thenReturn(true);

        assertThat(conditions.enabled()).isTrue();
    }

    @Test
    public void displayed() {
        assertThat(conditions.displayed()).isFalse();

        when(webElement1.isDisplayed()).thenReturn(true);

        assertThat(conditions.displayed()).isTrue();
    }

    @Test
    public void selected() {
        assertThat(conditions.selected()).isFalse();

        when(webElement2.isSelected()).thenReturn(true);

        assertThat(conditions.selected()).isTrue();
    }

    @Test
    public void text() {
        when(webElement3.getText()).thenReturn("Some Text");

        assertThat(conditions.text().contains("Some Text")).isTrue();
        assertThat(conditions.text().contains("Other Text")).isFalse();
    }

    @Test
    public void textContains() {
        when(webElement1.getText()).thenReturn("Some Text");

        assertThat(conditions.text().contains("Te")).isTrue();
        assertThat(conditions.text().contains("Other")).isFalse();
    }

    @Test
    public void attributeValue() {
        assertThat(conditions.attribute("attr", "value")).isFalse();

        when(webElement2.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.attribute("attr", "value")).isTrue();
    }

    @Test
    public void attribute() {
        assertThat(conditions.attribute("attr").equalTo("value")).isFalse();

        when(webElement2.getAttribute("attr")).thenReturn("value");

        assertThat(conditions.attribute("attr").equalTo("value")).isTrue();
    }

    @Test
    public void idValue() {
        assertThat(conditions.id("value")).isFalse();

        when(webElement3.getAttribute("id")).thenReturn("value");

        assertThat(conditions.id("value")).isTrue();
    }

    @Test
    public void id() {
        assertThat(conditions.id().equalTo("value")).isFalse();

        when(webElement3.getAttribute("id")).thenReturn("value");

        assertThat(conditions.id().equalTo("value")).isTrue();
    }

    @Test
    public void nameValue() {
        assertThat(conditions.name("value")).isFalse();

        when(webElement1.getAttribute("name")).thenReturn("value");

        assertThat(conditions.name("value")).isTrue();
    }

    @Test
    public void name() {
        assertThat(conditions.name().equalTo("value")).isFalse();

        when(webElement1.getAttribute("name")).thenReturn("value");

        assertThat(conditions.name().equalTo("value")).isTrue();
    }

    @Test
    public void tagNameValue() {
        assertThat(conditions.tagName("value")).isFalse();

        when(webElement1.getTagName()).thenReturn("value");

        assertThat(conditions.tagName("value")).isTrue();
    }

    @Test
    public void tagName() {
        assertThat(conditions.tagName().equalTo("value")).isFalse();

        when(webElement1.getTagName()).thenReturn("value");

        assertThat(conditions.tagName().equalTo("value")).isTrue();
    }

    @Test
    public void valueValue() {
        assertThat(conditions.value("value")).isFalse();

        when(webElement1.getAttribute("value")).thenReturn("value");

        assertThat(conditions.value("value")).isTrue();
    }

    @Test
    public void value() {
        assertThat(conditions.value().equalTo("value")).isFalse();

        when(webElement1.getAttribute("value")).thenReturn("value");

        assertThat(conditions.value().equalTo("value")).isTrue();
    }

    @Test
    public void hasSize() {
        assertThat(conditions.size(3)).isTrue();
        assertThat(conditions.size().equalTo(3)).isTrue();
        assertThat(conditions.size(2)).isFalse();
        assertThat(conditions.size().equalTo(2)).isFalse();

        assertThat(conditions.not().size(3)).isFalse();
        assertThat(conditions.not().size().equalTo(3)).isFalse();
        assertThat(conditions.not().size(2)).isTrue();
        assertThat(conditions.not().size().equalTo(2)).isTrue();

        AtLeastOneElementConditions conditions2 = new AtLeastOneElementConditions(
                Arrays.asList(fluentWebElement1, fluentWebElement3));

        assertThat(conditions2.size(3)).isFalse();
        assertThat(conditions2.size().equalTo(3)).isFalse();
        assertThat(conditions2.size(2)).isTrue();
        assertThat(conditions2.size().equalTo(2)).isTrue();
    }

    @Test
    public void className() {
        when(webElement1.getAttribute("class")).thenReturn("some-class-1 some-class-3");
        when(webElement2.getAttribute("class")).thenReturn("some-class-1 some-class-2 some-class-3");
        when(webElement3.getAttribute("class")).thenReturn("some-class-1");

        assertThat(conditions.className("some-class-1")).isTrue();
        assertThat(conditions.className("some-class-2")).isTrue();
        assertThat(conditions.className("some-class-4")).isFalse();
    }

    @Test
    public void defaultValueWhenEmpty() {
        AtLeastOneElementConditions emptyConditions = new AtLeastOneElementConditions(Arrays.asList());

        assertThat(emptyConditions.enabled()).isFalse();
    }
}
