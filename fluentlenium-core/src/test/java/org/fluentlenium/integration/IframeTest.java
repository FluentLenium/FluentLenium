package org.fluentlenium.integration;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.integration.localtest.LocalFluentCase;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IframeTest extends LocalFluentCase {

    @Page
    private IFramePage iFramePage;

    @Test
    public void should_get_element_into_a_frame_with_native_instructions() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").getText()).isEqualTo("Heading 1");
        getDriver().switchTo().frame("iframe1");
        assertThat($("h1").getText()).isEqualTo("Heading");
        assertThat($("#oneline").getText()).isEqualTo("A single line of text");
        getDriver().switchTo().defaultContent();
        assertThat($("h2").getText()).isEqualTo("Heading 2");
    }

    @Test
    public void should_get_element_into_a_frame_with_Fluent_switchTo() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").getText()).isEqualTo("Heading 1");
        iFramePage.switchTo(findFirst("#iframe1"));
        assertThat($("h1").getText()).isEqualTo("Heading");
        assertThat($("#oneline").getText()).isEqualTo("A single line of text");
        iFramePage.switchTo(null);
        assertThat($("h2").getText()).isEqualTo("Heading 2");
    }

    @Test
    public void should_get_element_into_a_frame_with_Fluent_switchTo_bis() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").getText()).isEqualTo("Heading 1");
        iFramePage.switchTo(findFirst("#iframe1"));
        assertThat($("h1").getText()).isEqualTo("Heading");
        assertThat($("#oneline").getText()).isEqualTo("A single line of text");
        iFramePage.switchTo();
        assertThat($("h2").getText()).isEqualTo("Heading 2");
    }

    @Test
    public void should_get_element_into_a_frame_with_Fluent_switchToDefault() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").getText()).isEqualTo("Heading 1");
        iFramePage.switchTo(findFirst("#iframe1"));
        assertThat($("h1").getText()).isEqualTo("Heading");
        assertThat($("#oneline").getText()).isEqualTo("A single line of text");
        iFramePage.switchToDefault();
        assertThat($("h2").getText()).isEqualTo("Heading 2");
    }

}

class IFramePage extends FluentPage {
    @Override
    public String getUrl() {
        return LocalFluentCase.BASE_URL + "iframe.html";
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).endsWith("Container");
    }
}
