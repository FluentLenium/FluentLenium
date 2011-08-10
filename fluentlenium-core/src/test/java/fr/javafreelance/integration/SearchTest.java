package fr.javafreelance.integration;


import fr.javafreelance.fluentlenium.core.domain.FluentList;
import fr.javafreelance.fluentlenium.core.domain.FluentWebElement;
import fr.javafreelance.integration.localTest.LocalFluentCase;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SearchTest extends LocalFluentCase {

    @Test
    public void checkSearchWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".small");
        assertThat(list.getIds()).contains("id", "id2");
    }

    @Test
    public void checkSearchOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.find(".child").getTexts()).containsOnly("Alex");
    }


    @Test
    public void checkSearchOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent");
        assertThat(element.find(".child").getTexts()).containsOnly("Alex");
    }

    @Test
    public void checkSearchFirstOnListWorks() {
        goTo(DEFAULT_URL);
        FluentList list = find(".parent");
        assertThat(list.findFirst(".child").getText()).isEqualTo("Alex");
    }


    @Test
    public void checkSearchFirstOnElementWorks() {
        goTo(DEFAULT_URL);
        FluentWebElement element = findFirst(".parent");
        assertThat(element.findFirst(".child").getText()).isEqualTo("Alex");
    }
}
