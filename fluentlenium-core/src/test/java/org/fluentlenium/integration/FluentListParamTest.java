package org.fluentlenium.integration;


import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentListParamTest extends IntegrationFluentTest {

    @Test
    public void checkTextsParam() {
        goTo(DEFAULT_URL);
        FluentList list = find("span");
        assertThat(list.getTexts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void checkNamesAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getNames()).contains("name", "name2");
    }

    @Test
    public void checkIdsAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }


    @Test
    public void checkAttributesAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.getAttributes("value")).contains("John", "Doe");
    }

    @Test
    public void checkValuesAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.getValues()).contains("John", "Doe");
    }


    @Test
    public void checkTextParam() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getText()).isEqualTo("Small 1");
    }

    @Test
    public void checkValueAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.getValue()).isEqualTo("John");
    }

    @Test
    public void checkAttributeAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.getAttribute("value")).isEqualTo("John");
    }

    @Test
    public void checkIdAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getId()).isEqualTo("id");
    }

    @Test
    public void checkNameAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getName()).isEqualTo("name");
    }
}
