package org.fluentlenium.integration;

import org.fluentlenium.core.FluentControl;
import org.fluentlenium.core.components.ComponentInstantiator;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectionFindTest extends IntegrationFluentTest {

    public static class ParentElement extends FluentWebElement {
        @FindBy(css = ".child")
        private FluentWebElement child;

        /**
         * Creates a new fluent web element.
         *
         * @param element      underlying element
         * @param control      controle interface
         * @param instantiator component instantiator
         */
        public ParentElement(WebElement element, FluentControl control, ComponentInstantiator instantiator) {
            super(element, control, instantiator);
        }
    }

    @Test
    public void testFindChild() {
        goTo(DEFAULT_URL);

        ParentElement elt = el(".parent").as(ParentElement.class);

        assertThat(elt.child).isNotNull();
        assertThat(elt.child.present());
    }

    @Test
    public void testNotFindChild() {
        goTo(DEFAULT_URL);

        ParentElement elt = el("#select").as(ParentElement.class);

        assertThat(elt.child).isNotNull();
        assertThat(!elt.child.present());
    }

    @Test
    public void testFindChildUsingList() {
        goTo(DEFAULT_URL);

        ParentElement elt = $(".parent").as(ParentElement.class).first();

        assertThat(elt.child).isNotNull();
        assertThat(elt.child.present());
    }

    @Test
    public void testNotFindChildUsingList() {
        goTo(DEFAULT_URL);

        ParentElement elt = $("#select").as(ParentElement.class).first();

        assertThat(elt.child).isNotNull();
        assertThat(!elt.child.present());
    }
}
