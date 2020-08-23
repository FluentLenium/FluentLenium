package org.fluentlenium.test.htmldump;

import org.apache.commons.io.IOUtils;
import org.fluentlenium.test.IntegrationFluentTest;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

class HtmlDumpTest extends IntegrationFluentTest {

    private final Path tempDir;

    HtmlDumpTest() throws IOException {
        getConfiguration().setHtmlDumpMode(TriggerMode.AUTOMATIC_ON_FAIL);
        tempDir = Files.createTempDirectory("tempfiles");
    }

    @Test
    void checkHtmlIsDumped() throws IOException {
        goTo(DEFAULT_URL);

        File file = new File(tempDir.toFile(), "test.html");
        try {
            takeHtmlDump(file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            String html = IOUtils.toString(fis, "UTF-8");
            assertThat(html).isEqualTo(el("html").html()).isNotEmpty();
        } finally {
            file.delete();
        }
    }
}
