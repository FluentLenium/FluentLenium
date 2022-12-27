package io.fluentlenium.test.injection;

import io.fluentlenium.core.FluentControl;
import io.fluentlenium.core.components.ComponentInstantiator;
import io.fluentlenium.core.domain.FluentWebElement;
import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

class InjectionFindTest extends IntegrationFluentTest {

    static class ParentElement extends FluentWebElement {
        @FindBy(css = ".child")
        private FluentWebElement child;

        /**
         * Creates a new fluent web element.
         *
         * @param element      underlying element
         * @param control      controle interface
         * @param instantiator component instantiator
         */
        ParentElement(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    @Test
    void testFindChild() {
        goTo(DEFAULT_URL);

        ParentElement elt = el(".parent").as(ParentElement.class);

        assertThat(elt.child).isNotNull();
        assertThat(elt.child.present()).isTrue();
    }

    @Test
    void testNotFindChild() {
        goTo(DEFAULT_URL);

        ParentElement elt = el("#select").as(ParentElement.class);

        assertThat(elt.child).isNotNull();
        assertThat(elt.child.present()).isFalse();
    }

    @Test
    void testFindChildUsingList() {
        goTo(DEFAULT_URL);

        ParentElement elt = $(".parent").as(ParentElement.class).first();

        assertThat(elt.child).isNotNull();
        assertThat(elt.child.present()).isTrue();
    }

    @Test
    void testNotFindChildUsingList() {
        goTo(DEFAULT_URL);

        ParentElement elt = $("#select").as(ParentElement.class).first();

        assertThat(elt.child).isNotNull();
        assertThat(elt.child.present()).isFalse();
    }
}
