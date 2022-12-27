package io.fluentlenium.test.actions.onlist;

import io.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ActionOnListTest extends IntegrationFluentTest {

    @Test
    void checkFillAction() {
        goTo(DEFAULT_URL);
        $("input[type=text]").write("zzz");

        assertThat($("input[type=text]").values()).contains("zzz");
    }

    @Test
    void checkScrollIntoView() {
        goTo(DEFAULT_URL);

        $("#name").scrollIntoView();
        el("#name").scrollIntoView();

        $("#name").scrollIntoView(false);
        el("#name").scrollIntoView(false);
    }

    @Test
    void checkClearAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").values()).contains("John");
        $("#name").clear();
        assertThat($("#name").values()).contains("");
    }

    @Test
    void checkClickAction() {
        goTo(DEFAULT_URL);
        assertThat(window().title()).contains("Selenium");
        $("#linkToPage2").click();
        assertThat(window().title()).isEqualTo("Page 2");
    }

    @Test
    void checkTextAction() {
        goTo(DEFAULT_URL);
        assertThat($("#name").values()).contains("John");
        assertThat($(".small").texts()).contains("Small 1", "Small 2", "Small 3");
    }

    @Test
    void checkFillFileInputUpperCase(@TempDir Path tempDir) throws IOException {
        Path numbers = tempDir.resolve("numbers.txt");
        Files.write(numbers, Arrays.asList("1", "2", "3"));

        goTo(DEFAULT_URL);
        $("#fileUpload2").fill().with(numbers.toString());
        assertThat($("#fileUpload2").first().value()).endsWith("numbers.txt");
    }
}
