package org.fluentlenium.integration;

import org.fluentlenium.adapter.FluentTest;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class AxesTest extends LocalFluentCase {

    @Test
    public void checkSearchParentWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent > .child");
        assertThat(element.axes().parent().getAttribute("class")).isEqualTo("parent");
    }

    @Test
    public void checkSearchAncestorsWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("html > body > .parent > .child");
        FluentList<FluentWebElement> ancestors = element.axes().ancestors();
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
        FluentList<FluentWebElement> descendants = element.axes().descendants();
        assertThat(descendants.size()).isGreaterThan(10);
    }

    @Test
    public void checkSearchPrecedingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> precedings = element.axes().precedings();
        assertThat(precedings.size()).isGreaterThan(2);

        Collections.reverse(precedings);

        assertThat(precedings.get(0).getTagName()).isEqualTo("option");
        assertThat(precedings.get(1).getTagName()).isEqualTo("span");
    }

    @Test
    public void checkSearchPrecedingSiblingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> precedings = element.axes().precedingSiblings();
        assertThat(precedings).hasSize(1);

        assertThat(precedings.get(0).getTagName()).isEqualTo("option");
        assertThat(precedings.get(0).getAttribute("value")).isEqualTo("value-1");
    }

    @Test
    public void checkSearchFollowingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> followings = element.axes().followings();
        assertThat(followings.size()).isGreaterThan(2);

        assertThat(followings.get(0).getTagName()).isEqualTo("option");
        assertThat(followings.get(1).getTagName()).isEqualTo("input");
    }

    @Test
    public void checkSearchFollowingSiblingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst("#select > option[value='value-2']");
        FluentList<FluentWebElement> followings = element.axes().followingSiblings();
        assertThat(followings).hasSize(1);

        assertThat(followings.get(0).getTagName()).isEqualTo("option");
        assertThat(followings.get(0).getAttribute("value")).isEqualTo("value-3");
    }
}
