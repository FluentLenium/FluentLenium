package org.fluentlenium.integration;

import org.fluentlenium.integration.localtest.IntegrationFluentTest;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ActionOnListTest extends IntegrationFluentTest {

    @Test
    public void checkFillAction() {
        goTo(DEFAULT_URL);
        $("input[type=text]").text("zzz");

        assertThat($("input[type=text]").getValues()).contains("zzz");
    }

    @Test
    public void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        $("#name").clear();
        assertThat($("#name").getValues()).contains("");
    }

    @Test
    public void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(title()).contains("Selenium");
        $("#linkToPage2").click();
        assertThat(title()).isEqualTo("Page 2");
    }


    @Test
    public void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").getValues()).contains("John");
        assertThat($(".small").getTexts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    public void checkFillFileInput() {
        goTo(DEFAULT_URL);
        $("#fileUpload").fill().with("/data/fileName");
        assertThat($("#fileUpload").getValue()).endsWith("fileName");
    }

    @Test
    public void checkFillFileInputUpperCase() {
        goTo(DEFAULT_URL);
        $("#fileUpload2").fill().with("/data/fileName");
        assertThat($("#fileUpload2").getValue()).endsWith("fileName");
    }
}
