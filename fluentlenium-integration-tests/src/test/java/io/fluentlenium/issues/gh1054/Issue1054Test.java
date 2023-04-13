package io.fluentlenium.issues.gh1054;

import io.fluentlenium.test.IntegrationFluentTest;
import io.fluentlenium.utils.UrlUtils;
import org.junit.jupiter.api.Test;

import static io.fluentlenium.assertj.FluentLeniumAssertions.assertThat;

public class Issue1054Test extends IntegrationFluentTest {
    @Test
    public void canCheckForAbsentAlert() {
        goTo(UrlUtils.getAbsoluteUrlFromFile("page1054.html"));

        assertThat(alert()).isNotPresent();
    }

    @Test
    public void canCheckForAlert() {
        goTo(UrlUtils.getAbsoluteUrlFromFile("page1054.html"));

        $("button").click();

        assertThat(alert()).isPresent();
    }

    @Test
    public void canCheckForAlertText() {
        goTo(UrlUtils.getAbsoluteUrlFromFile("page1054.html"));

        $("button").click();

        assertThat(alert()).hasText("Hello! I am an alert box!!");
    }

}