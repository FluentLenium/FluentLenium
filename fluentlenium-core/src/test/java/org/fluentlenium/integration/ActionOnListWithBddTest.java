package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentList;
import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionOnListWithBddTest extends LocalFluentCase {


    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        FluentList input = find("input[type=text]");
        input.fill().with("zzz");
        assertThat(input.getValues()).contains("zzz");
    }

    @Test
    public void checkFillActionOnElement() {
        goTo(DEFAULT_URL);
        FluentWebElement input = find("input").first();
        input.fill().with("zzz");
        assertThat(input.getValue()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        FluentList name = find("#name");
        assertThat(name.getValues()).contains("John");
        name.clear();
        assertThat(name.getValues()).contains("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        FluentList name = find("#linkToPage2");
        assertThat(title()).contains("Selenium");
        name.click();
        assertThat(title()).isEqualTo("Page 2");
    }


}
