package io.fluentlenium.test.fluentlist;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.filter.FilterConstructor;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.Select;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

@SuppressWarnings("unchecked")
class SearchOnlyWithFiltersTest extends IntegrationFluentTest {

    @Test
    void checkWithNameWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(FilterConstructor.withName("name"));
        assertThat(list.ids()).containsOnly("id");
    }

    @Test
    void checkWithTextWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(FilterConstructor.withText("Pharmacy"));
        assertThat(list.ids()).containsOnly("location");
    }

    @Test
    void checkWithTextStartsWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(FilterConstructor.withText().startsWith("Pharmac"));
        assertThat(list.ids()).containsOnly("location");
    }

    @Test
    void checkWithTextContentStartsWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(FilterConstructor.withText().startsWith("Pharmac"));
        assertThat(list.ids()).containsOnly("location");
    }

    @Test
    void checkIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(FilterConstructor.withClass("small")).index(1);
        assertThat(element.loaded()).isTrue();
        assertThat(element.id()).isEqualTo("id2");
    }

    @Test
    void checkFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(FilterConstructor.withClass("small"));
        assertThat(element.id()).isEqualTo("id");
    }

    @Test
    void checkMultipleWithWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(FilterConstructor.withClass("small"), FilterConstructor.withName("name"));
        assertThat(list.ids()).containsOnly("id");
    }

    @Test
    void checkDollarWorks() {
        goTo(DEFAULT_URL);
        FluentList list = $(FilterConstructor.withName("name"));
        assertThat(list.ids()).containsOnly("id");
    }

    @Test
    void checkDollarAndIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = $(FilterConstructor.withClass("small")).index(1);
        assertThat(element.id()).isEqualTo("id2");
    }

    @Test
    void checkFillWorks() {
        goTo(DEFAULT_URL);
        $(FilterConstructor.withId("name")).fill().with("FillTest");
        assertThat($("#name").first().value()).isEqualTo("FillTest");
    }

    @Test
    void checkFillSelectWorks() {
        goTo(DEFAULT_URL);
        Select select = new Select(el("#select").getElement());
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 1");
        $(FilterConstructor.withId("select")).fillSelect().withValue("value-3");
        assertThat(select.getFirstSelectedOption().getText()).isEqualTo("value 3");
    }

    @Test
    void checkClickWorks() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        $(FilterConstructor.withId("linkToPage2")).click();
        assertThat(window().title()).isEqualTo("Page 2");
    }

    @Test
    void checkClearWorks() {
        goTo(DEFAULT_URL);
        assertThat($("#name").first().value()).isEqualTo("John");
        $(FilterConstructor.withId("name")).clear();
        assertThat($("#name").first().value()).isEmpty();
    }

    @Test
    void checkTextWorks() {
        goTo(DEFAULT_URL);
        assertThat($(FilterConstructor.withName("name")).texts()).containsOnly("Small 1");
    }

    @Test
    void checkValueWorks() {
        goTo(DEFAULT_URL);
        assertThat($(FilterConstructor.withId("name")).values()).containsOnly("John");
    }

    @Test
    void checkFindChildFindWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(FilterConstructor.withClass("parent")).find(FilterConstructor.withClass("child"));
        assertThat(list.texts()).containsOnly("Alex");
    }

    @Test
    void checkFindChildFindWithIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(FilterConstructor.withClass("parent")).find(FilterConstructor.withClass("child")).index(0);
        assertThat(element.text()).isEqualTo("Alex");
    }

    @Test
    void checkFindChildFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = find(FilterConstructor.withClass("parent")).el(FilterConstructor.withClass("child"));
        assertThat(element.text()).isEqualTo("Alex");
    }

    @Test
    void checkFindFirstChildFindWorks() {
        goTo(DEFAULT_URL);
        FluentList list = el(FilterConstructor.withClass("parent")).find(FilterConstructor.withClass("child"));
        assertThat(list.texts()).containsOnly("Alex");
    }

    @Test
    void checkFindFirstChildFindWithIndexWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(FilterConstructor.withClass("parent")).find(FilterConstructor.withClass("child")).index(0);
        assertThat(element.text()).isEqualTo("Alex");
    }

    @Test
    void checkFindFirstChildFindFirstWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(FilterConstructor.withClass("parent")).el(FilterConstructor.withClass("child"));
        assertThat(element.text()).isEqualTo("Alex");
    }
}
