package io.fluentlenium.test.axes;

import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

class AxesTest extends IntegrationFluentTest {

    @Test
    void checkSearchParentWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el(".parent > .child");
        Assertions.assertThat(element.dom().parent().attribute("class")).isEqualTo("parent");
    }

    @Test
    void checkSearchAncestorsWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("html > body > .parent > .child");
        FluentList<FluentWebElement> ancestors = element.dom().ancestors();
        assertThat(ancestors).hasSize(3);

        assertThat(ancestors.get(0).tagName()).isEqualTo("html");
        assertThat(ancestors.get(1).tagName()).isEqualTo("body");
        assertThat(ancestors.get(2).tagName()).isEqualTo("span");
        assertThat(ancestors.get(2).attribute("class")).isEqualTo("parent");
    }

    @Test
    void checkSearchDescendantsWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("html");
        FluentList<FluentWebElement> descendants = element.dom().descendants();
        assertThat(descendants).hasSizeGreaterThan(10);
    }

    @Test
    void checkSearchPrecedingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("#select > option[value='value-2']");
        FluentList<FluentWebElement> precedings = element.dom().precedings();
        assertThat(precedings).hasSizeGreaterThan(2);

        Collections.reverse(precedings);

        assertThat(precedings.get(0).tagName()).isEqualTo("option");
        assertThat(precedings.get(1).tagName()).isEqualTo("span");
    }

    @Test
    void checkSearchPrecedingSiblingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("#select > option[value='value-2']");
        FluentList<FluentWebElement> precedings = element.dom().precedingSiblings();
        assertThat(precedings).hasSize(1);

        assertThat(precedings.get(0).tagName()).isEqualTo("option");
        assertThat(precedings.get(0).attribute("value")).isEqualTo("value-1");
    }

    @Test
    void checkSearchFollowingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("#select > option[value='value-2']");
        FluentList<FluentWebElement> followings = element.dom().followings();
        assertThat(followings).hasSizeGreaterThan(2);

        assertThat(followings.get(0).tagName()).isEqualTo("option");
        assertThat(followings.get(1).tagName()).isEqualTo("input");
    }

    @Test
    void checkSearchFollowingSiblingWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = el("#select > option[value='value-2']");
        FluentList<FluentWebElement> followings = element.dom().followingSiblings();
        assertThat(followings).hasSize(1);

        assertThat(followings.get(0).tagName()).isEqualTo("option");
        assertThat(followings.get(0).attribute("value")).isEqualTo("value-3");
    }
}
