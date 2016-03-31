package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementAsTest extends LocalFluentCase {

    public static class Component extends FluentWebElement {

        public Component(WebElement webElement) {
            super(webElement);
        }

    }

    public static class NotAComponent extends FluentWebElement {
        public NotAComponent(String invalidConstructorParam) {
            super(null);
        }
    }

    @Test
    public void testAsComponent() {
        goTo(DEFAULT_URL);
        Component span = findFirst("span").as(Component.class);
        assertThat(span).isNotNull();

        FluentList<Component> spans = find("span").as(Component.class);
        assertThat(spans).isNotEmpty();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAsNotAComponent() {
        goTo(DEFAULT_URL);
        findFirst("span").as(NotAComponent.class);
    }

}
