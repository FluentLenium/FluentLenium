package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.By;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends IntegrationFluentTest {

    @Test
    public void checkSearchWorks() {
        goTo(DEFAULT_URL);
        final FluentList list = find(".small");
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    public void checkSearchByLocatorWorks() {
        goTo(DEFAULT_URL);
        final By locator = By.cssSelector(".small");
        final FluentList list = find(locator);
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListWorks() {
        goTo(DEFAULT_URL);
        final FluentList list = find(".parent");
        assertThat(list.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnListByLocatorWorks() {
        goTo(DEFAULT_URL);
        final FluentList list = find(".parent");
        final By locator = By.cssSelector(".child");
        assertThat(list.find(locator).texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementWorks() {
        goTo(DEFAULT_URL);
        final FluentWebElement element = el(".parent");
        assertThat(element.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementByLocatorWorks() {
        goTo(DEFAULT_URL);
        final By locator = By.cssSelector(".parent");
        final FluentWebElement element = el(locator);
        assertThat(element.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListWorks() {
        goTo(DEFAULT_URL);
        final FluentList list = find(".parent");
        assertThat(list.el(".child").text()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnListByLocatorWorks() {
        goTo(DEFAULT_URL);
        final FluentList list = find(".parent");
        final By locator = By.cssSelector(".child");
        assertThat(list.el(locator).text()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnElementWorks() {
        goTo(DEFAULT_URL);
        final FluentWebElement element = el(".parent");
        assertThat(element.el(".child").text()).isEqualTo("Alex");
    }
}
