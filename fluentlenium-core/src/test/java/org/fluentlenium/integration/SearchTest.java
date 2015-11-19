package org.fluentlenium.integration;


import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest extends LocalFluentCase {

    @Test
    public void checkSearchWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.find(".child").getTexts()).containsOnly("Alex");
    }


    @Test
    public void checkSearchOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent");
        assertThat(element.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.findFirst(".child").getText()).isEqualTo("Alex");
    }


    @Test
    public void checkSearchFirstOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent");
        assertThat(element.findFirst(".child").getText()).isEqualTo("Alex");
    }
}
