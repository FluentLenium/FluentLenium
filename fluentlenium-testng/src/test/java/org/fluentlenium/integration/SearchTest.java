package org.fluentlenium.integration;


import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.integration.localtest.LocalFluentCase;
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
}
