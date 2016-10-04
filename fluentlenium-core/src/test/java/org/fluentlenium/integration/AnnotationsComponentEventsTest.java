package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.events.annotations.AfterClickOn;
import org.fluentlenium.core.events.annotations.AfterFindBy;
import org.fluentlenium.core.events.annotations.BeforeClickOn;
import org.fluentlenium.core.events.annotations.BeforeFindBy;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AnnotationsComponentEventsTest extends AnnotationsComponentEventsTestSubClass {
    private List<WebElement> beforeClick = new ArrayList<>();

    public static class Component extends FluentWebElement {

        private int beforeClick = 0;
        private int afterClick = 0;

        private List<By> beforeFindBy = new ArrayList<>();
        private List<By> afterFindBy = new ArrayList<>();

        public Component(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }

        @BeforeClickOn
        public void beforeClickOn() {
            assertThat(afterClick).isEqualTo(beforeClick);
            beforeClick++;
        }

        @AfterClickOn
        public void afterClickOn() {
            assertThat(beforeClick).isEqualTo(afterClick + 1);
            afterClick++;
        }

        @BeforeFindBy
        public void beforeFindBy(By by) {
            beforeFindBy.add(by);
        }

        @AfterFindBy
        public void afterFindBy(By by) {
            assertThat(beforeFindBy).hasSize(afterFindBy.size() + 1);
            afterFindBy.add(by);
        }
    }

    @Test
    public void clickOnFirst() {
        goTo(DEFAULT_URL);

        Component button = el("button").as(Component.class);
        button.click();

        Component otherButton = el("button").as(Component.class);

        assertThat(button.beforeClick).isEqualTo(1);
        assertThat(button.afterClick).isEqualTo(1);

        assertThat(otherButton.beforeClick).isEqualTo(0);
        assertThat(otherButton.afterClick).isEqualTo(0);

        assertThat(beforeClick).containsExactly(unwrapElement(button.getElement()));
        assertThat(afterClick).containsExactly(unwrapElement(button.getElement()));

    }

    private WebElement unwrapElement(WebElement element) {
        if (element instanceof WrapsElement) {
            WebElement wrappedElement = ((WrapsElement) element).getWrappedElement();
            if (wrappedElement != element && wrappedElement != null) {
                return unwrapElement(wrappedElement);
            }
        }
        return element;
    }

    private List<WebElement> unwrapElements(List<WebElement> elements) {
        ArrayList<WebElement> unwrapElements = new ArrayList<>();
        for (WebElement element : elements) {
            unwrapElements.add(unwrapElement(element));
        }
        return unwrapElements;
    }

    @Test
    public void clickOn() {
        goTo(DEFAULT_URL);

        FluentList<Component> buttons = $("button").as(Component.class);
        buttons.click();

        FluentList<Component> otherButtons = $("button").as(Component.class);

        for (Component button : buttons) {
            assertThat(button.beforeClick).isEqualTo(1);
            assertThat(button.afterClick).isEqualTo(1);
        }

        for (Component button : otherButtons) {
            assertThat(button.beforeClick).isEqualTo(0);
            assertThat(button.afterClick).isEqualTo(0);
        }

        List<WebElement> elements = new ArrayList<>();
        for (Component button : buttons) {
            elements.add(unwrapElement(button.getElement()));
        }

        assertThat(beforeClick).containsExactlyElementsOf(elements);
        assertThat(afterClick).containsExactlyElementsOf(elements);

    }

    @BeforeClickOn
    private void beforeClickOn(FluentWebElement element) {
        beforeClick.add(element.getElement());
    }

    @Test
    public void findBy() {
        goTo(DEFAULT_URL);

        Component htmlComponent = el("html").as(Component.class);
        htmlComponent.el("button").present();

        Component otherHtmlComponent = el("html").as(Component.class);
        otherHtmlComponent.present();

        assertThat(htmlComponent.beforeFindBy).hasSize(1);
        assertThat(htmlComponent.afterFindBy).hasSize(1);

        assertThat(htmlComponent.beforeFindBy).containsExactly(By.cssSelector("button"));
        assertThat(htmlComponent.afterFindBy).containsExactly(By.cssSelector("button"));

        assertThat(otherHtmlComponent.beforeFindBy).isEmpty();
        assertThat(otherHtmlComponent.afterFindBy).isEmpty();
    }
}
