package io.fluentlenium.test.component;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.FluentPage;
import io.fluentlenium.core.annotation.Page;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.core.hook.wait.Wait;
import io.fluentlenium.core.inject.Parent;
import io.fluentlenium.pages.Page2;
import io.fluentlenium.test.IntegrationFluentTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
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
    void testComponents() {
        goTo(COMPONENTS_URL);

        Assertions.assertThat(header.loaded()).isFalse();
        Assertions.assertThat(content.loaded()).isFalse();
        Assertions.assertThat(footer.loaded()).isFalse();

        Assertions.assertThat(header.present()).isTrue();
        Assertions.assertThat(content.present()).isTrue();
        Assertions.assertThat(footer.present()).isTrue();

        Assertions.assertThat(header.loaded()).isTrue();
        Assertions.assertThat(content.loaded()).isTrue();
        Assertions.assertThat(footer.loaded()).isTrue();

        Assertions.assertThat(header.title.text()).isEqualTo("Header Title");
        Assertions.assertThat(footer.copyright.text()).isEqualTo("(c) FluentLenium");

        Assertions.assertThat(content.page.pageContent.text()).isEqualTo("Page Content");
        assertThat(content.components).hasSize(2);

        assertThat(content.page.parent).isSameAs(content);

        int i = 1;
        for (Component component : content.components) {
            assertThat(component.parent).isSameAs(content);
            Assertions.assertThat(component.title.text()).isEqualTo("Component " + i);

            assertThat(component.subComponent).isNotNull();
            Assertions.assertThat(component.subComponent.action.text()).isEqualTo("Sub Component " + i + " (" + 1 + ")");
            assertThat(component.subComponents).hasSize(3);
            int j = 1;
            for (SubComponent subComponent : component.subComponents) {
                assertThat(subComponent.parent).isSameAs(component);
                Assertions.assertThat(subComponent.action.text()).isEqualTo("Sub Component " + i + " (" + j + ")");
                j++;
            }
            i++;
        }

        Assertions.assertThat(content.notPresent.present()).isFalse();

    }

    @Test
    void shouldInstantiatePage() {
        goTo(COMPONENTS_URL);
        Assertions.assertThat(footer.createPageInstance()).isInstanceOf(Page2.class);
    }

    @Test
    void shouldBeAbleToAccessDriver() {
        goTo(COMPONENTS_URL);
        assertThat(header.getUrl()).contains("components.html");
    }

    public static class Header extends FluentWebElement {
        @FindBy(css = ".title")
        private FluentWebElement title;

        public Header(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }

        String getUrl() {
            return getDriver().getCurrentUrl();
        }
    }

    public static class Footer extends FluentWebElement {
        @FindBy(css = ".copyright")
        private FluentWebElement copyright;

        public Footer(WebElement webElement, FluentControl fluentControl, ComponentInstantiator instantiator) {
            super(webElement, fluentControl, instantiator);
        }

        Page2 createPageInstance() {
            return newInstance(Page2.class);
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

    private static class ComponentsPage extends FluentPage {
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
