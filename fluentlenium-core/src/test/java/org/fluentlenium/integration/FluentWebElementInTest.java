package org.fluentlenium.integration;

import org.fluentlenium.core.domain.FluentWebElement;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FluentWebElementInTest extends LocalFluentCase {
    FluentWebElement linkToPage2;

    @Test
    public void when_web_element_in_test_then_they_are_instanciated() {
        goTo(LocalFluentCase.DEFAULT_URL);
        linkToPage2.click();
        assertThat(url()).isEqualTo(LocalFluentCase.BASE_URL + "page2.html");
    }


}
