package org.fluentlenium.integration;


import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import java.util.Collections;

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

    @Test
    public void checkSearchDescendantsWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("html");
        FluentList<FluentWebElement> descendants = element.findDescendants();
        assertThat(descendants.size()).isGreaterThan(10);
    }

    @Test
    public void checkSearchPrecedingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> precedings = element.findPrecedings();
        assertThat(precedings.size()).isGreaterThan(2);

        Collections.reverse(precedings);

        assertThat(precedings.get(0).getTagName()).isEqualTo("option");
        assertThat(precedings.get(1).getTagName()).isEqualTo("span");
    }

    @Test
    public void checkSearchPrecedingSiblingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> precedings = element.findPrecedingsSiblings();
        assertThat(precedings).hasSize(1);

        assertThat(precedings.get(0).getTagName()).isEqualTo("option");
        assertThat(precedings.get(0).getAttribute("value")).isEqualTo("value-1");
    }

    @Test
    public void checkSearchFollowingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> followings = element.findFollowings();
        assertThat(followings.size()).isGreaterThan(2);

        assertThat(followings.get(0).getTagName()).isEqualTo("option");
        assertThat(followings.get(1).getTagName()).isEqualTo("input");
    }

    @Test
    public void checkSearchFollowingSiblingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> followings = element.findFollowingSiblings();
        assertThat(followings).hasSize(1);

        assertThat(followings.get(0).getTagName()).isEqualTo("option");
        assertThat(followings.get(0).getAttribute("value")).isEqualTo("value-3");
    }
}
