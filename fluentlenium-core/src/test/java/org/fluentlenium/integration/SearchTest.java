package org.fluentlenium.integration;


import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
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

    @Test
    public void checkSearchParentWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent > .child");
        assertThat(element.findParent().getAttribute("class")).isEqualTo("parent");
    }

    @Test
    public void checkSearchAncestorsWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("html > body > .parent > .child");
        FluentList<FluentWebElement> ancestors = element.findAncestors();
        assertThat(ancestors).hasSize(3);

        assertThat(ancestors.get(0).getTagName()).isEqualTo("html");
        assertThat(ancestors.get(1).getTagName()).isEqualTo("body");
        assertThat(ancestors.get(2).getTagName()).isEqualTo("span");
        assertThat(ancestors.get(2).getAttribute("class")).isEqualTo("parent");
    }
}
