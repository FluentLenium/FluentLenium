package io.fluentlenium.test.injection;

import io.fluentlenium.core.FluentControl;import io.fluentlenium.core.FluentPage;import io.fluentlenium.core.annotation.Page;import io.fluentlenium.core.components.ComponentInstantiator;import io.fluentlenium.core.domain.FluentWebElement;import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

class InjectionTest extends IntegrationFluentTest {
    static class TestChildComponent extends FluentWebElement {
        @FindBy(css = ".parent > .child")
        private FluentWebElement element;

        TestChildComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    static class TestComponent extends FluentWebElement {
        private TestChildComponent childComponent;

        @Page
        private TestPageOfComponent page;

        TestComponent(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    private static class TestPage extends FluentPage {
        private TestComponent component;
    }

    private static class TestPageOfComponent extends FluentPage {
        private TestChildComponent component;
    }

    @Page
    private TestPage page;

    @Test
    void testCapabilities() {
        goTo(DEFAULT_URL);

        assertThat(page.component.childComponent.element).isNotNull();
        assertThat(page.component.page).isNotNull();
        assertThat(page.component.page.component.element).isNotNull();
    }
}
