package io.fluentlenium.test.component;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentException;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.components.ComponentsManager;
import io.fluentlenium.core.domain.FluentList;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElementAsTest extends IntegrationFluentTest {
    @FindBy(css = "a.go-next")
    private Component goNextLink;

    @FindBy(css = "a.go-next")
    private ComponentNotAnElement goNextLink2;

    @Test
    void testAsComponent() {
        goTo(DEFAULT_URL);
        Component span = el("span").as(Component.class);
        assertThat(span).isNotNull();

        FluentList<Component> spans = find("span").as(Component.class);
        assertThat(spans).isNotEmpty();
    }

    @Test
    void testAsNotAComponent() {
        assertThrows(ComponentException.class,
                () -> {
                    goTo(DEFAULT_URL);
                    el("span").as(NotAComponent.class);
                });
    }

    @Test
    void testAsDefaultConstructorComponent() {
        assertThrows(ComponentException.class,
                () -> {
                    goTo(DEFAULT_URL);
                    el("span").as(InvalidComponent.class);
                });
    }

    @Test
    void testAsFullConstructorComponent() {
        goTo(DEFAULT_URL);
        FullConstructorComponent component = el("span").as(FullConstructorComponent.class);

        Assertions.assertThat(component.fluentControl).isSameAs(this);
        assertThat(component.element.getTagName()).isEqualTo("span");
        Assertions.assertThat(component.instantiator).isInstanceOf(ComponentsManager.class);
    }

    @Test
    void findByComponent() {
        goTo(DEFAULT_URL);
        Assertions.assertThat(goNextLink.displayed()).isTrue();
    }

    @Test
    void findByComponentNotFluentWebElement() {
        goTo(DEFAULT_URL);
        assertThat(goNextLink2.isDisplayed()).isTrue();
    }

    public static class Component extends FluentWebElement {
        public Component(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class ComponentNotAnElement {

        private final WebElement element;

        public ComponentNotAnElement(WebElement webElement) {
            element = webElement;
        }

        boolean isDisplayed() {
            return element.isDisplayed();
        }

    }

    public static class NotAComponent extends FluentWebElement {
        public NotAComponent(String invalidConstructorParam) { // NOPMD UnusedFormalParameter
            super(null, null, null);
        }
    }

    public static class FullConstructorComponent {

        private final WebElement element;
        private final ComponentInstantiator instantiator;
        private final FluentControl fluentControl;

        public FullConstructorComponent(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            element = webElement;
            this.fluentControl = fluentControl;
            this.instantiator = instantiator;
        }

    }

    private static class InvalidComponent {
    }

}
