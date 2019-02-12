package org.fluentlenium.test.actions.attributemodification;

import static org.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

class AttributeModificationTest extends IntegrationFluentTest {

    @Test
    void checkAttributesModification() {
        goTo(DEFAULT_URL);

        FluentWebElement singleElement = el("#time");
        assertThat(singleElement).hasAttributeValue("type", "time");

        singleElement.modifyAttribute("type", "text");
        assertThat(singleElement).hasAttributeValue("type", "text");
    }

    @Test
    void checkAttributeModificationOnList() {
        goTo(DEFAULT_URL);
        FluentList<FluentWebElement> fluentWebElements = $(".small");
        assertThat(fluentWebElements).hasId("id");

        fluentWebElements.modifyAttribute("id", "newId");
        assertThat(fluentWebElements).hasId("newId");
    }

}
