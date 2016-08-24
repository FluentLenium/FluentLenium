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
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkSearcByLocatorhWorks() {
        goTo(DEFAULT_URL);
        By locator = By.cssSelector(".small");
        FluentList list = find(locator);
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnListByLocatorWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.find(locator).getTexts()).containsOnly("Alex");
    }


    @Test
    public void checkSearchOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent");
        assertThat(element.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchOnElementByLocatorWorks() {
        goTo(DEFAULT_URL);
        By locator = By.cssSelector(".parent");
        FluentWebElement element = findFirst(locator);
        assertThat(element.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.findFirst(".child").getText()).isEqualTo("Alex");
    }

    @Test
    public void checkSearchFirstOnListByLocatorWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        By locator = By.cssSelector(".child");
        assertThat(list.findFirst(locator).getText()).isEqualTo("Alex");
    }


    @Test
    public void checkSearchFirstOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent");
        assertThat(element.findFirst(".child").getText()).isEqualTo("Alex");
    }
}
