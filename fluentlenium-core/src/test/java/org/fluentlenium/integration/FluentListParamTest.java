package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class FluentListParamTest extends IntegrationFluentTest {

    @Test
    public void checkTextsParam() {
        goTo(DEFAULT_URL);
        FluentList list = find("span");
        assertThat(list.texts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void checkNamesAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.names()).contains("name", "name2");
    }

    @Test
    public void checkIdsAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.ids()).contains("id", "id2");
    }

    @Test
    public void checkAttributesAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.attributes("value")).contains("John", "Doe");
    }

    @Test
    public void checkValuesAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.values()).contains("John", "Doe");
    }

    @Test
    public void checkTextParam() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.first().text()).isEqualTo("Small 1");
    }

    @Test
    public void checkSingleAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThatThrownBy(() -> list.single().value())
                .hasMessageContaining("Elements By.cssSelector: input")
                .hasMessageContaining("there are [ 10 ] elements instead");

        assertThat($("#id2").single().text()).contains("Small 2");
    }

    @Test
    public void checkAttributeAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.first().attribute("value")).isEqualTo("John");
    }

    @Test
    public void checkIdAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.first().id()).isEqualTo("id");
    }

    @Test
    public void checkNameAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.first().name()).isEqualTo("name");
    }
}
