package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.support.ui.Select;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentlenium.core.filter.FilterConstructor.withClass;
import static org.fluentlenium.core.filter.FilterConstructor.withId;
import static org.fluentlenium.core.filter.FilterConstructor.withName;
import static org.fluentlenium.core.filter.FilterConstructor.withText;

public class SearchOnlyWithFiltersTest extends IntegrationFluentTest {

    @Test
    public void checkWithNameWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withName("name"));
        assertThat(list.ids()).containsOnly("id");
    }

    @Test
    public void checkWithTextWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withText("Pharmacy"));
        assertThat(list.ids()).containsOnly("location");
    }

    @Test
    public void checkWithTextStartsWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withText().startsWith("Pharmac"));
        assertThat(list.ids()).containsOnly("location");
    }

    @Test
    public void checkWithTextContentStartsWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withText().startsWith("Pharmac"));
        assertThat(list.ids()).containsOnly("location");
    }

    @Test
    public void checkIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(withClass("small")).index(1);
        assertThat(element.loaded()).isFalse();
        assertThat(element.id()).isEqualTo("id2");
    }

    @Test
    public void checkFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(withClass("small"));
        assertThat(element.id()).isEqualTo("id");
    }

    @Test
    public void checkMultipleWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withClass("small"), withName("name"));
        assertThat(list.ids()).containsOnly("id");
    }

    @Test
    public void checkDollarWorks() {
        goTo(DEFAULT_URL);
        FluentList list = $(withName("name"));
        assertThat(list.ids()).containsOnly("id");
    }

    @Test
    public void checkDollarAndIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = $(withClass("small")).index(1);
        assertThat(element.id()).isEqualTo("id2");
    }

    @Test
    public void checkFillWorks() {
        goTo(DEFAULT_URL);
        $(withId("name")).fill().with("FillTest");
        assertThat($("#name").first().value()).isEqualTo("FillTest");
    }

    @Test
    public void checkFillSelectWorks() {
        goTo(DEFAULT_URL);
        Select select = new Select(el("#select").getElement());
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        $(withId("select")).fillSelect().withValue("value-3");
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
    }

    @Test
    public void checkClickWorks() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        $(withId("linkToPage2")).click();
        assertThat(window().title()).isEqualTo("Page 2");
    }

    @Test
    public void checkClearWorks() {
        goTo(DEFAULT_URL);
        assertThat($("#name").first().value()).isEqualTo("John");
        $(withId("name")).clear();
        assertThat($("#name").first().value()).isEqualTo("");
    }

    @Test
    public void checkTextWorks() {
        goTo(DEFAULT_URL);
        assertThat($(withName("name")).texts()).containsOnly("Small 1");
    }

    @Test
    public void checkValueWorks() {
        goTo(DEFAULT_URL);
        assertThat($(withId("name")).values()).containsOnly("John");
    }

    @Test
    public void checkFindChildFindWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(withClass("parent")).find(withClass("child"));
        assertThat(list.texts()).containsOnly("Alex");
    }

    @Test
    public void checkFindChildFindWithIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(withClass("parent")).find(withClass("child")).index(0);
        assertThat(element.text()).isEqualTo("Alex");
    }

    @Test
    public void checkFindChildFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(withClass("parent")).el(withClass("child"));
        assertThat(element.text()).isEqualTo("Alex");
    }

    @Test
    public void checkFindFirstChildFindWorks() {
        goTo(DEFAULT_URL);
        FluentList list = el(withClass("parent")).find(withClass("child"));
        assertThat(list.texts()).containsOnly("Alex");
    }

    @Test
    public void checkFindFirstChildFindWithIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(withClass("parent")).find(withClass("child")).index(0);
        assertThat(element.text()).isEqualTo("Alex");
    }

    @Test
    public void checkFindFirstChildFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(withClass("parent")).el(withClass("child"));
        assertThat(element.text()).isEqualTo("Alex");
    }
}
