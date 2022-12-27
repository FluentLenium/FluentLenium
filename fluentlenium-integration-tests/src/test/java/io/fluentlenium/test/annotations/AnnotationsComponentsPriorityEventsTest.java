package io.fluentlenium.test.annotations;

import io.fluentlenium.core.FluentControl;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.core.components.ComponentInstantiator;import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.events.annotations.AfterClickOn;import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.events.annotations.AfterClickOn;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationsComponentsPriorityEventsTest extends IntegrationFluentTest {
    @Page
    private TestPage page;

    @FindBy(css = "button.class1.class2.class3")
    private TestComponent component;

    @FindBy(css = "#oneline")
    private TestComponent component2;

    private static List<String> clicks = new ArrayList<>();

    public static class TestPage {
        @AfterClickOn(5)
        public void afterClick(FluentWebElement element) {
            clicks.add("page");
        }

        @AfterClickOn(25)
        public void afterClick2(FluentWebElement element) {
            clicks.add("page");
        }
    }

    public static class TestComponent extends FluentWebElement {

        /**
         * Creates a new fluent web element.
         *
         * @param element      underlying element
         * @param control      control interface
         * @param instantiator component instantiator
         */
        public TestComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }

        @AfterClickOn(10)
        public void afterClick() {
            clicks.add("component");
        }

        @AfterClickOn(15)
        public void afterClick2() {
            clicks.add("component");
        }
    }

    public static class TestComponent2 extends FluentWebElement {

        /**
         * Creates a new fluent web element.
         *
         * @param element      underlying element
         * @param control      control interface
         * @param instantiator component instantiator
         */
        public TestComponent2(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }

        @AfterClickOn(15)
        public void afterClick() {
            clicks.add("component2");
        }
    }

    @Test
    void testEventsPriority() {
        goTo(DEFAULT_URL);
        component.click();

        assertThat(clicks).containsExactly("page", "component", "component", "page");
    }
}
