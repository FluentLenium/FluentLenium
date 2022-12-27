package io.fluentlenium.test.fluentlist;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("unchecked")
class SearchTest extends IntegrationFluentTest {

    @Test
    void checkSearchWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    void checkSearchByLocatorWorks() {
        goTo(DEFAULT_URL);
        By locator = By.cssSelector(".small");
        FluentList list = find(locator);
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    void checkSearchOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    void checkSearchOnListByLocatorWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.find(locator).texts()).containsOnly("Alex");
    }

    @Test
    void checkSearchOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(".parent");
        assertThat(element.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    void checkSearchOnElementByLocatorWorks() {
        goTo(DEFAULT_URL);
        By locator = By.cssSelector(".parent");
        FluentWebElement element = el(locator);
        assertThat(element.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    void checkSearchFirstOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        Assertions.assertThat(list.el(".child").text()).isEqualTo("Alex");
    }

    @Test
    void checkSearchFirstOnListByLocatorWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        Assertions.assertThat(list.el(locator).text()).isEqualTo("Alex");
    }

    @Test
    void checkSearchFirstOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(".parent");
        assertThat(element.el(".child").text()).isEqualTo("Alex");
    }
}
