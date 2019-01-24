package org.fluentlenium.test.iframe;

import org.fluentlenium.core.FluentPage;
import org.fluentlenium.core.annotation.Page;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IframeTest extends IntegrationFluentTest {

    @Page
    private IFramePage iFramePage;

    @Test
    void shouldGetElementIntoAFrameWithNativeInstructions() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").first().text()).isEqualTo("Heading 1");
        getDriver().switchTo().frame("iframe1");
        assertThat($("h1").first().text()).isEqualTo("Heading");
        assertThat($("#oneline").first().text()).isEqualTo("A single line of text");
        getDriver().switchTo().defaultContent();
        assertThat($("h2").first().text()).isEqualTo("Heading 2");
    }

    @Test
    void shouldGetElementIntoAFrameWithFluentSwitchTo() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").first().text()).isEqualTo("Heading 1");
        iFramePage.switchTo(el("#iframe1"));
        assertThat($("h1").first().text()).isEqualTo("Heading");
        assertThat($("#oneline").first().text()).isEqualTo("A single line of text");
        iFramePage.switchToDefault();
        assertThat($("h2").first().text()).isEqualTo("Heading 2");
    }

    @Test
    void shouldGetElementIntoAFrameWithFluentSwitchToBis() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").first().text()).isEqualTo("Heading 1");
        iFramePage.switchTo(el("#iframe1"));
        assertThat($("h1").first().text()).isEqualTo("Heading");
        assertThat($("#oneline").first().text()).isEqualTo("A single line of text");
        iFramePage.switchTo();
        assertThat($("h2").first().text()).isEqualTo("Heading 2");
    }

    @Test
    void shouldGetElementIntoAFrameWithFluentSwitchToDefault() {
        // Given
        // When
        iFramePage.go();
        // Then
        iFramePage.isAt();
        assertThat($("h1").first().text()).isEqualTo("Heading 1");
        iFramePage.switchTo(el("#iframe1"));
        assertThat($("h1").first().text()).isEqualTo("Heading");
        assertThat($("#oneline").first().text()).isEqualTo("A single line of text");
        iFramePage.switchToDefault();
        assertThat($("h2").first().text()).isEqualTo("Heading 2");
    }

}

class IFramePage extends FluentPage {
    @Override
    public String getUrl() {
        return IntegrationFluentTest.IFRAME_URL;
    }

    @Override
    public void isAt() {
        assertThat(getDriver().getTitle()).endsWith("Container");
    }
}
