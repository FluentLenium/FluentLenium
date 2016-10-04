package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.core.hook.wait.Wait;
import org.fluentlenium.core.inject.Parent;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Wait
public class ComponentsTest extends IntegrationFluentTest {
    @FindBy(css = ".header")
    private Header header;

    private Content content;

    @FindBy(css = ".footer")
    private Footer footer;

    @Test
    public void testComponents() {
        goTo(COMPONENTS_URL);

        assertThat(header.loaded()).isFalse();
        assertThat(content.loaded()).isFalse();
        assertThat(footer.loaded()).isFalse();

        assertThat(header.present()).isTrue();
        assertThat(content.present()).isTrue();
        assertThat(footer.present()).isTrue();

        assertThat(header.loaded()).isTrue();
        assertThat(content.loaded()).isTrue();
        assertThat(footer.loaded()).isTrue();

        assertThat(header.title.text()).isEqualTo("Header Title");
        assertThat(footer.copyright.text()).isEqualTo("(c) FluentLenium");

        assertThat(content.page.pageContent.text()).isEqualTo("Page Content");
        assertThat(content.components).hasSize(2);

        assertThat(content.page.parent).isSameAs(content);

        int i = 1;
        for (Component component : content.components) {
            assertThat(component.parent).isSameAs(content);
            assertThat(component.title.text()).isEqualTo("Component " + i);

            assertThat(component.subComponent).isNotNull();
            assertThat(component.subComponent.action.text()).isEqualTo("Sub Component " + i + " (" + 1 + ")");
            assertThat(component.subComponents).hasSize(3);
            int j = 1;
            for (SubComponent subComponent : component.subComponents) {
                assertThat(subComponent.parent).isSameAs(component);
                assertThat(subComponent.action.text()).isEqualTo("Sub Component " + i + " (" + j + ")");
                j++;
            }
            i++;
        }

        assertThat(content.notPresent.present()).isFalse();

    }

    public static class Header extends FluentWebElement {
        @FindBy(css = ".title")
        private FluentWebElement title;

        public Header(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class Footer extends FluentWebElement {
        @FindBy(css = ".copyright")
        private FluentWebElement copyright;

        public Footer(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    @FindBy(css = ".component")
    public static class Component extends FluentWebElement {
        @Parent
        private Content parent;

        @FindBy(css = ".title")
        private FluentWebElement title;

        @FindBy(css = ".sub-component")
        private List<SubComponent> subComponents;

        @FindBy(css = ".sub-component")
        private SubComponent subComponent;

        public Component(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }
    }

    public static class SubComponent {
        private final WebElement webElement;

        @Parent
        private Component parent;

        @FindBy(css = "a.action")
        private FluentWebElement action;

        public SubComponent(WebElement webElement) {
            this.webElement = webElement;
        }
    }

    public static class ComponentsPage extends FluentPage {
        @FindBy(css = ".page > .page-content")
        private FluentWebElement pageContent;

        @Parent
        private Content parent;
    }

    @FindBy(css = ".content")
    public static class Content extends FluentWebElement {
        @Page
        private ComponentsPage page;

        private List<Component> components;

        private FluentWebElement notPresent;

        public Content(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }

    }
}
