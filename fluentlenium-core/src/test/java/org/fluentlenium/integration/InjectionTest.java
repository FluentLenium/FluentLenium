package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectionTest extends IntegrationFluentTest {
    public static class TestChildComponent extends FluentWebElement {
        @FindBy(css = ".parent > .child")
        private FluentWebElement element;

        public TestChildComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    public static class TestComponent extends FluentWebElement {
        private TestChildComponent childComponent;

        @Page
        private TestPageOfComponent page;

        public TestComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    public static class TestPage extends FluentPage {
        private TestComponent component;
    }

    public static class TestPageOfComponent extends FluentPage {
        private TestChildComponent component;
    }

    @Page
    private TestPage page;

    @Test
    public void testCapabilities() {
        goTo(DEFAULT_URL);

        assertThat(page.component.childComponent.element).isNotNull();
        assertThat(page.component.page).isNotNull();
        assertThat(page.component.page.component.element).isNotNull();
    }
}
