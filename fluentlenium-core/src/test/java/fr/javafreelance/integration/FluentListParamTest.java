package fr.javafreelance.integration;


import fr.javafreelance.fluentlenium.core.domain.FluentList;
import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class FluentListParamTest extends LocalFluentCase {

    @Test
    public void checkTextParam() {
        goTo(DEFAULT_URL);
        FluentList list = find("span");
        assertThat(list.getTexts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void checkValueAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getNames()).contains("name", "name2");
    }

    @Test
    public void checkIdAction() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkNameAction() {
        goTo(DEFAULT_URL);
        FluentList list = find("input");
        assertThat(list.getValues()).contains("John", "Doe");
    }
}
