package org.fluentlenium.adapter.testng.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.adapter.testng.integration.localtest.LocalFluentCase;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends LocalFluentCase {

    @BeforeMethod
    public void beforeTest() {
        goTo(DEFAULT_URL);
    }

    @Test
    public void checkSearchWorks() {
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListWorks() {
        FluentList list = find(".parent");
        assertThat(list.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementWorks() {
        FluentWebElement element = findFirst(".parent");
        assertThat(element.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListWorks() {
        FluentList list = find(".parent");
        assertThat(list.findFirst(".child").getText()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnElementWorks() {
        FluentWebElement element = findFirst(".parent");
        assertThat(element.findFirst(".child").getText()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchByLocatorWorks() {
        By locator = By.cssSelector(".small");
        FluentList list = find(locator);
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListByLocatorWorks() {
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.find(locator).getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementByLocatorWorks() {
        By locator = By.cssSelector(".parent");
        FluentWebElement element = findFirst(locator);
        assertThat(element.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListByLocatorWorks() {
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.findFirst(locator).getText()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnElementByLocatorWorks() {
        By locator = By.cssSelector(".parent");
        FluentWebElement element = findFirst(locator);
        assertThat(element.findFirst(".child").getText()).isEqualTo("Alex");
    }

}
