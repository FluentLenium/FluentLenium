package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.adapter.testng.integration.localtest.IntegrationFluentTestNg;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends IntegrationFluentTestNg {

    @BeforeMethod
    public void beforeTest() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkSearchWorks() {
        FluentList list = find(".small");
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListWorks() {
        FluentList list = find(".parent");
        assertThat(list.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementWorks() {
        FluentWebElement element = el(".parent");
        assertThat(element.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListWorks() {
        FluentList list = find(".parent");
        assertThat(list.el(".child").text()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnElementWorks() {
        FluentWebElement element = el(".parent");
        assertThat(element.el(".child").text()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchByLocatorWorks() {
        By locator = By.cssSelector(".small");
        FluentList list = find(locator);
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListByLocatorWorks() {
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.find(locator).texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementByLocatorWorks() {
        By locator = By.cssSelector(".parent");
        FluentWebElement element = el(locator);
        assertThat(element.find(".child").texts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListByLocatorWorks() {
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.el(locator).text()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnElementByLocatorWorks() {
        By locator = By.cssSelector(".parent");
        FluentWebElement element = el(locator);
        assertThat(element.el(".child").text()).isEqualTo("Alex");
    }

}
