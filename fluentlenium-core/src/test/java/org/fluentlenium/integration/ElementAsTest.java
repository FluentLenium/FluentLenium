package org.fluentlenium.integration;

import org.fluentlenium.core.components.ComponentException;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.components.ComponentsManager;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class ElementAsTest extends LocalFluentCase {

    public static class Component extends FluentWebElement {
        public Component(WebElement webElement, WebDriver driver, ComponentInstantiator instantiator) {
            super(webElement, driver, instantiator);
        }
    }

    public static class ComponentNotAnElement {

        private final WebElement element;

        public ComponentNotAnElement(WebElement webElement) {
            this.element = webElement;
        }

        public boolean isDisplayed() {
            return this.element.isDisplayed();
        }

    }

    public static class NotAComponent extends FluentWebElement {
        public NotAComponent(String invalidConstructorParam) {
            super(null, null, null);
        }
    }

    public static class FullConstructorComponent {

        private final WebElement element;
        private final ComponentInstantiator instantiator;
        private final WebDriver driver;

        public FullConstructorComponent(WebElement webElement, WebDriver driver, ComponentInstantiator instantiator) {
            this.element = webElement;
            this.driver = driver;
            this.instantiator = instantiator;
        }

    }

    public static class InvalidComponent {
    }

    @FindBy(css = "a.go-next")
    private Component goNextLink;

    @FindBy(css = "a.go-next")
    private ComponentNotAnElement goNextLink2;

    @Test
    public void testAsComponent() {
        goTo(DEFAULT_URL);
        Component span = findFirst("span").as(Component.class);
        assertThat(span).isNotNull();

        FluentList<Component> spans = find("span").as(Component.class);
        assertThat(spans).isNotEmpty();
    }

    @Test(expected = ComponentException.class)
    public void testAsNotAComponent() {
        goTo(DEFAULT_URL);
        findFirst("span").as(NotAComponent.class);
    }

    @Test(expected = ComponentException.class)
    public void testAsDefaultConstructorComponent() {
        goTo(DEFAULT_URL);
        InvalidComponent span = findFirst("span").as(InvalidComponent.class);
    }

    @Test
    public void testAsFullConstructorComponent() {
        goTo(DEFAULT_URL);
        FullConstructorComponent component = findFirst("span").as(FullConstructorComponent.class);

        assertThat(component.driver).isSameAs(getDriver());
        assertThat(component.element.getTagName()).isEqualTo("span");
        assertThat(component.instantiator).isInstanceOf(ComponentsManager.class);
    }

    @Test
    public void findByComponent() {
        goTo(DEFAULT_URL);
        assertThat(goNextLink.isDisplayed()).isTrue();
    }

    @Test
    public void findByComponentNotFluentWebElement() {
        goTo(DEFAULT_URL);
        assertThat(goNextLink2.isDisplayed()).isTrue();
    }

}
